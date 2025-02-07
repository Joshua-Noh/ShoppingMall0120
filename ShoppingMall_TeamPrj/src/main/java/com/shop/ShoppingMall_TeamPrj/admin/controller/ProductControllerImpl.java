package com.shop.ShoppingMall_TeamPrj.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.admin.service.ProductService;
import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

@Controller("ProductController")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productservice;
    
    // 관리자 기능 접근 시, 로그인 상태 및 관리자 권한 체크
    private boolean isAdminLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        MemberVO member = (MemberVO) session.getAttribute("memberInfo");
        if (member == null) return false;
        return "ADMIN".equalsIgnoreCase(member.getRole());
    }
    
    @RequestMapping(value = "/admin/*Form.do", method = RequestMethod.GET)
    private ModelAndView form(@RequestParam(value = "result", required = false) String result,
                              @RequestParam(value = "action", required = false) String action,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isAdminLoggedIn(request)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        String viewName = (String) request.getAttribute("viewName");
        HttpSession session = request.getSession();
        session.setAttribute("action", action);  
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        mav.setViewName(viewName);
        return mav;
    }
    
    @RequestMapping(value = "/admin/updateProductForm.do", method = RequestMethod.GET)
    private ModelAndView updateProductForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isAdminLoggedIn(request)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        String viewName = (String) request.getAttribute("viewName");
        String productId = request.getParameter("productId");
        ProductVO product = productservice.updateProductFormData(Integer.parseInt(productId));
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.setViewName(viewName);
        return mav;
    }
    
    @Override
    @RequestMapping(value= "/admin/listProducts.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isAdminLoggedIn(request)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        HttpSession session = request.getSession(false);
        MemberVO loginMember = (MemberVO) session.getAttribute("memberInfo");
        if (!"ADMIN".equalsIgnoreCase(loginMember.getRole())) {
            return new ModelAndView("redirect:/main/main.do");
        }
        String viewName = (String) request.getAttribute("viewName");
        List productList = productservice.listProducts();
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("productList", productList);
        return mav;
    }

    @Override
    @RequestMapping(value = "/admin/addProduct.do", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("product") ProductVO product,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if (!isAdminLoggedIn(request)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        int result = productservice.addproduct(product);
        return new ModelAndView("redirect:/admin/imageUploadForm.do");
    }
    
    @Override
    @RequestMapping(value = "/admin/removeProduct.do", method = RequestMethod.GET)
    public ModelAndView removeProduct(@RequestParam("productId") int id, 
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        if (!isAdminLoggedIn(request)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        productservice.removeProduct(id);
        return new ModelAndView("redirect:/admin/listProducts.do");
    }
    
    @Override
    @RequestMapping(value = "/admin/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isAdminLoggedIn(request)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        int result = productservice.updateProduct(product);
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        if (result > 0) {
            mav.addObject("message", "수정 성공");
        } else {
            mav.addObject("message", "수정 실패");
        }
        return mav;
    }
    
    @RequestMapping(value="/admin/imageUpload.do")
    public String form() {
        return "imageUploadForm";  // imageUploadForm.jsp
    }
    
    @RequestMapping(value="/admin/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        if (!isAdminLoggedIn(multipartRequest)) {
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        Map<String, Object> map = new HashMap<String, Object>(); 
        String category = multipartRequest.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            category = "default";
        }
        List<String> fileList = fileProcess(multipartRequest, category);
        map.put("fileList", fileList);
        return new ModelAndView("redirect:/admin/listProducts.do");
    }
    
    private List<String> fileProcess(MultipartHttpServletRequest multipartRequest, String category) throws Exception {
        List<String> fileList = new ArrayList<String>();
        String uploadPath = CURR_IMAGE_REPO_PATH + "\\" + category;
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileParameterName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileParameterName);
            String originalFileName = mFile.getOriginalFilename();
            fileList.add(originalFileName);
            if (mFile.getSize() != 0) {
                File file = new File(uploadPath + "\\" + originalFileName);
                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                mFile.transferTo(file);
            }
        }
        return fileList;
    }
    
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping_project\\image_repo";
}
