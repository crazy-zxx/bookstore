package com.me.servlet;

import com.google.gson.Gson;
import com.me.bean.*;
import com.me.service.AccountService;
import com.me.service.BookService;
import com.me.service.UserService;
import com.me.web.BookStoreWebUtils;
import com.me.web.CriteriaBook;
import com.me.web.Page;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name = "bookServlet", value = "/bookServlet")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        //请求参数中指明要调用的方法
        String methodName = req.getParameter("method");
        try {
            //反射获取方法对象
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //设置可调用
            method.setAccessible(true);
            //调用方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
    }


    private UserService userService = new UserService();

    /**
     * 处理支付请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求中的用户名和账户名
        String username = request.getParameter("username");
        String accountId = request.getParameter("accountId");

        //错误消息
        //提交的表单属性值校验
        StringBuffer errors = validateFormField(username, accountId);
        if (errors.toString().equals("")) {
            //用户和账户对应校验
            errors = validateUser(username, accountId);
            if (errors.toString().equals("")) {
                //库存校验
                errors = validateBookStoreNumber(request);
                if (errors.toString().equals("")) {
                    //账户余额校验
                    errors = validateBalance(request, accountId);
                }
            }
        }
        //校验失败
        if (!errors.toString().equals("")) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
            return;
        }
        //校验通过
        //处理支付
        bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId);
        response.sendRedirect(request.getContextPath() + "/success.jsp");
    }

    private AccountService accountService = new AccountService();

    /**
     * 校验账户余额
     *
     * @param request
     * @param accountId
     * @return
     */
    public StringBuffer validateBalance(HttpServletRequest request, String accountId) {
        //错误消息
        StringBuffer errors = new StringBuffer("");
        //从session中获取购物车对象
        ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
        //获取账户对象
        Account account = accountService.getAccount(Integer.parseInt(accountId));
        //比较购物车总价和账户余额
        if (cart.getTotalMoney() > account.getBalance()) {
            errors.append("余额不足！<br>");
        }

        return errors;
    }

    /**
     * 校验商品库存
     *
     * @param request
     * @return
     */
    public StringBuffer validateBookStoreNumber(HttpServletRequest request) {
        //错误消息
        StringBuffer errors = new StringBuffer("");
        //从session中获取购物车对象
        ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);

        //遍历购物车条目，校验每个条目商品的库存
        for (ShoppingCartItem sci : cart.getItems()) {
            int quantity = sci.getQuantity();
            int storeNumber = bookService.getBook(sci.getBook().getBookId()).getStoreNumber();
            //比较购买数量和库存数量
            if (quantity > storeNumber) {
                errors.append(sci.getBook().getTitle()).append("库存不足！<br>");
            }
        }

        return errors;
    }

    /**
     * 校验用户名和账户名
     *
     * @param username
     * @param accountId
     * @return
     */
    public StringBuffer validateUser(String username, String accountId) {
        boolean flag = false;
        //获取用户对象
        User user = userService.getUserByUserName(username);
        if (user != null) {
            //获取该用户的账户对象
            int accountIdRight = user.getAccountId();
            if (accountId.trim().equals("" + accountIdRight)) {
                flag = true;
            }
        }
        //错误消息
        StringBuffer errors2 = new StringBuffer("");
        if (!flag) {
            errors2.append("账号不匹配！<br>");
        }

        return errors2;
    }

    /**
     * 校验提交的表单属性值
     *
     * @param username
     * @param accountId
     * @return
     */
    public StringBuffer validateFormField(String username, String accountId) {
        //错误消息
        StringBuffer errors = new StringBuffer("");
        //用户名属性值为空
        if (username == null || username.trim().equals("")) {
            errors.append("用户名输入无效<br>");
        }
        //账户名属性值为空
        if (accountId == null || accountId.trim().equals("")) {
            errors.append("账户名输入无效<br>");
        }

        return errors;
    }

    /**
     * 处理更新商品条目的数目请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求中的商品id和数目
        String bookIdStr = request.getParameter("bookId");
        String quantityStr = request.getParameter("quantity");
        //从session中获取购物车对象
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        //从字符串解析为数值
        int bookId = -1;
        int quantity = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        //数值合法，更新该商品数目
        if (bookId > 0 && quantity > 0) {
            bookService.updateItemQuantity(sc, bookId, quantity);
        }

        //构造Map，放入修改数目后的商品发生变动的属性
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("bookNumber", sc.getBookNumber());
        result.put("totalMoney", sc.getTotalMoney());
        //通过Map构造json对象
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        //将属性变动返回，用于修改页面显示
        response.setContentType("text/javascript");
        response.getWriter().print(jsonStr);
    }

    /**
     * 处理清空购物车请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取购物车对象
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        //清空购物车
        bookService.clearShoppingCart(sc);

        request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
    }

    /**
     * 处理移除指定商品的请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求中的商品id
        String bookIdStr = request.getParameter("bookId");
        //格式化为数值
        int bookId = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        //从session中获取购物车对象
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        //从购物车移除商品
        bookService.removeItemFromShoppingCart(sc, bookId);

        //购物车空
        if (sc.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
            return;
        }
        //购物车不空
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    /**
     * 转发请求到指定页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数中的待转发页面名
        String page = request.getParameter("page");
        request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
    }

    /**
     * 处理添加商品到购物车的请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求中的商品id
        String bookIdStr = request.getParameter("bookId");
        //格式化为数值
        int bookId = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        //flag标志添加是否成功
        boolean flag = false;
        if (bookId > 0) {
            //从session中获取购物车对象
            ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
            //添加到购物车
            flag = bookService.addToCart(bookId, sc);
        }
        //添加成功
        if (flag) {
            //重新获取当前指定条件的商品信息
            getBooks(request, response);
            return;
        }
        //添加失败
        response.sendRedirect(request.getContextPath() + "/error.jsp");
    }

    /**
     * 处理查询指定id的商品的请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求中的商品id
        String bookIdStr = request.getParameter("bookId");
        //格式化为数值
        int bookId = -1;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        //查询商品对象
        Book book = null;
        if (bookId > 0) {
            book = bookService.getBook(bookId);
        }
        //商品不存在
        if (book == null) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }
        //商品存在，保存到请求中
        request.setAttribute("book", book);
        //转发
        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
    }

    /**
     * 处理获取指定查询条件的商品的请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求中的查询参数
        String pageNoStr = request.getParameter("pageNo");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");

        //初始默认值
        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;
        //格式化为数值
        try {
            pageNo = Integer.parseInt(pageNoStr);
        } catch (NumberFormatException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        try {
            minPrice = Integer.parseInt(minPriceStr);
        } catch (NumberFormatException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }
        try {
            maxPrice = Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.warn(sw.toString());
        }

        //构造查询对象
        CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
        //获取查询结果
        Page<Book> page = bookService.getPage(criteriaBook);
        //将查询结果保存到请求中
        request.setAttribute("bookpage", page);
        //转发
        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
    }


}
