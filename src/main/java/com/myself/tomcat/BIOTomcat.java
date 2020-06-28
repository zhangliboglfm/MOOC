package com.myself.tomcat;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  使用 inputstream outputstream 来实现
 *
 */
public class BIOTomcat {





    /**
     * 封装http请求
     */
    public class MyRequest {

        private String url;
        private String method;

        public MyRequest(InputStream inputStream) throws IOException {

            String httpRequest = "";
            byte[] httpRequestBytes = new byte[1024];
            int length = 0;
            if((length = inputStream.read(httpRequestBytes)) > 0) {
                httpRequest = new String(httpRequestBytes, 0, length);
            }

            String httpHead = httpRequest.split("\n")[0];
            url = httpHead.split("\\s")[1];
            method = httpHead.split("\\s")[0];

            System.out.println(this.toString());
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        @Override
        public String toString() {
            return "MyRequest -- url:" + url + ",method:" + method;
        }

    }

    /**
     * 封装http响应
     */
    public class MyResponse {

        private OutputStream outputStream;

        public MyResponse (OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        public void write(String content) throws IOException {
            StringBuffer httpResponse = new StringBuffer();
            httpResponse.append("HTTP/1.1 200 OK\n")
                    .append("Content-Type: text/html\n")
                    .append("\r\n")
                    .append(content);

            outputStream.write(httpResponse.toString().getBytes());
            outputStream.close();
        }

    }



    /**
     * 定义Servlet映射POJO类
     */
    public class ServletMapping {

        private String servletName;
        private String url;
        private String className;

        public ServletMapping(String servletName, String url, String className) {
            super();
            this.servletName = servletName;
            this.url = url;
            this.className = className;
        }

        public String getServletName() {
            return servletName;
        }

        public void setServletName(String servletName) {
            this.servletName = servletName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

    }


    // 主类相关信息
    private int port;
    //保存请求url和处理请求servlet的对应关系
    private Map<String, String> urlServletMap = new HashMap<String, String>();

    public BIOTomcat(int port) {
        this.port = port;
    }

    public void start() {
        initServletMapping();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("MyTomcat is start...\n监听端口：" + port);

            while(true) {
                System.out.println("等待请求...");
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                MyRequest myRequest = new MyRequest(inputStream);
                MyResponse myResponse = new MyResponse(outputStream);

                //请求分发
                disPatch(myRequest, myResponse);
                socket.close();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化url和处理的servlet的对应关系
    private void initServletMapping() {
        List<ServletMapping> servletMappingList = new ArrayList<>();
        servletMappingList.add(new ServletMapping("Book", "/book", "com.myself.tomcat.BookServlet"));
        servletMappingList.add(new ServletMapping("Car", "/car", "com.myself.tomcat.CarServlet"));
        for(ServletMapping servletMapping: servletMappingList) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClassName());
        }
    }

    //分发处理请求
    private void disPatch(MyRequest myRequest, MyResponse myResponse) {
        String className = urlServletMap.get(myRequest.getUrl());

        //反射
        try {
            Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(className);
            MyServlet myServlet = myServletClass.newInstance();

            myServlet.service(myRequest, myResponse);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BIOTomcat myTomcat = new BIOTomcat(8080);
        myTomcat.start();
    }
}

/**
 * 处理操作'车'的http请求
 */
class CarServlet extends MyServlet {

    @Override
    public void doGet(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse) {
        try {
            myResponse.write("[get] car...");
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse) {
        try {
            myResponse.write("[post] car...");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
/**
 * 处理操作'书'的http请求
 */
class BookServlet extends MyServlet {

    @Override
    public void doGet(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse) {
        try {
            myResponse.write("[get] book...");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse) {
        try {
            myResponse.write("[post] book...");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}

/**
 * Servlet抽象类
 */
 abstract class MyServlet {

    public abstract void doGet(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse);

    public abstract void doPost(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse);

    public void service(BIOTomcat.MyRequest myRequest, BIOTomcat.MyResponse myResponse) {
        if(myRequest.getMethod().equalsIgnoreCase("POST")) {
            doPost(myRequest, myResponse);
        }else if(myRequest.getMethod().equalsIgnoreCase("GET")) {
            doGet(myRequest, myResponse);
        }
    }
}