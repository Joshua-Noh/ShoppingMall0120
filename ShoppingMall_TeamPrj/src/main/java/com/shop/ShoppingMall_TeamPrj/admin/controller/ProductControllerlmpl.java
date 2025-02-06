package com.shop.ShoppingMall_TeamPrj.admin.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.admin.service.ProductService;
import com.shop.ShoppingMall_TeamPrj.admin.vo.ProductVO;
import com.shop.ShoppingMall_TeamPrj.member.vo.MemberVO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller("ProductController")
public class ProductControllerlmpl  implements ProductController{
	
	@Autowired
	private ProductService productservice;
	
	//@Autowired
	//private ProductVO productVO;
	
    @RequestMapping(value = "/admin/*Form.do", method = RequestMethod.GET)
    private ModelAndView form(@RequestParam(value = "result", required = false) String result,
                              @RequestParam(value = "action", required = false) String action,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        String viewName = (String) request.getAttribute("viewName");
        String productId = request.getParameter("productId");
        ProductVO product = new ProductVO();
        product = productservice.updateProductFormData(Integer.parseInt(productId));
        		
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.setViewName(viewName);
        return mav;
    }
    
    @Override
    @RequestMapping(value= "/admin/listProducts.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
     
        if (session == null || session.getAttribute("memberInfo") == null) {
            return new ModelAndView("redirect:/main/main.do");
        }
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

        int result = productservice.addproduct(product);

        HttpSession session = request.getSession();
       
        
        return new ModelAndView("redirect:/admin/imageUploadForm.do");
    }
    @Override
    @RequestMapping(value = "/admin/removeProduct.do", method = RequestMethod.GET)
    public ModelAndView removeProduct(@RequestParam("productId") int id, 
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        // UTF-8 �몄��� �ㅼ�� (�대�� ��泥��� 吏������� ���쇰㈃ ���� 媛���)
        request.setCharacterEncoding("utf-8");
        // ��鍮��� �몄����� ���� ����
        productservice.removeProduct(id);
        // ���� �� ���� 紐⑸� ���댁�濡� 由щ�ㅼ�대����
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int result = productservice.updateProduct(product);
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        if (result > 0) {
            mav.addObject("message", "���� ��蹂� ���� �깃났");
        } else {
            mav.addObject("message", "���� ��蹂� ���� �ㅽ��");
        }
        return mav;
    }
 // �� ���� ���� 湲곕낯 寃쎈� (移댄��怨�由щ�濡� ���λ��)
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping_project\\image_repo";
    
    // �� ��濡��� �� ���댁� �대�� (���� 異�媛� �� 由щ������)
    @RequestMapping(value="/admin/imageUpload.do")
    public String form() {
        return "imageUploadForm";  // `imageUploadForm.jsp`濡� �대��
    }
    
    // �� ���� ��濡��� 泥�由� (移댄��怨�由� ���� �� ��濡���)
    @RequestMapping(value="/admin/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response) throws Exception {
        // ��湲� �몄��� �ㅼ��
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<String, Object>(); 
        
        // �� �ъ�⑹��媛� ������ 移댄��怨�由� 媛��몄�ㅺ린
        String category = multipartRequest.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            category = "湲고��";  // 湲곕낯 移댄��怨�由� �ㅼ�� (���� 泥�由�)
        }
        
        // �� ���� ���� (移댄��怨�由щ� �대���� ����)
        List<String> fileList = fileProcess(multipartRequest, category);
        map.put("fileList", fileList);
        
        // �� ��濡��� ��猷� �� ���� 紐⑸��쇰� �대��
        return new ModelAndView("redirect:/admin/listProducts.do");
    }
    
    // �� ���� ���� 泥�由� 硫����� (移댄��怨�由щ� �대�� ���� �� ����)
    private List<String> fileProcess(MultipartHttpServletRequest multipartRequest, String category) throws Exception {
        List<String> fileList = new ArrayList<String>();

        // �� 移댄��怨�由� �대�� 寃쎈� �ㅼ��
        String uploadPath = CURR_IMAGE_REPO_PATH + "\\" + category;
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();  // 移댄��怨�由� �대��媛� ���쇰㈃ ����
        }
        
        // �� ��濡����� ���� 泥�由�
        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileParameterName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileParameterName);
            String originalFileName = mFile.getOriginalFilename();
            fileList.add(originalFileName);
            
            if (mFile.getSize() != 0) { // ���쇱�� 議댁�ы���� 寃쎌�� ����
                File file = new File(uploadPath + "\\" + originalFileName);
                
                // ���쇱�� 議댁�ы��吏� ���쇰㈃ 遺�紐� ������由� ���� �� �� ���� ����
                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                // �� �ㅼ�� ���� ����
                mFile.transferTo(file);
            }
        }
        return fileList;
    }
    
    
}