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
		String viewName = (String)request.getAttribute("viewName");
		List productList = productservice.listProducts();
		ModelAndView mav = new ModelAndView(viewName);
		//System.out.println("articlesList : "+ articlesList);
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
        // UTF-8 인코딩 설정 (이미 요청에 지정되어 있으면 생략 가능)
        request.setCharacterEncoding("utf-8");
        // 서비스 호출하여 회원 삭제
        productservice.removeProduct(id);
        // 삭제 후 회원 목록 페이지로 리다이렉트
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int result = productservice.updateProduct(product);
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        if (result > 0) {
            mav.addObject("message", "회원 정보 수정 성공");
        } else {
            mav.addObject("message", "회원 정보 수정 실패");
        }
        return mav;
    }
 // ✅ 파일 저장 기본 경로 (카테고리별로 저장됨)
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping_project\\image_repo";
    
    // ✅ 업로드 폼 페이지 이동 (상품 추가 후 리디렉션)
    @RequestMapping(value="/admin/imageUpload.do")
    public String form() {
        return "imageUploadForm";  // `imageUploadForm.jsp`로 이동
    }
    
    // ✅ 파일 업로드 처리 (카테고리 선택 후 업로드)
    @RequestMapping(value="/admin/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response) throws Exception {
        // 한글 인코딩 설정
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<String, Object>(); 
        
        // ✅ 사용자가 선택한 카테고리 가져오기
        String category = multipartRequest.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            category = "기타";  // 기본 카테고리 설정 (예외 처리)
        }
        
        // ✅ 파일 저장 (카테고리별 폴더에 저장)
        List<String> fileList = fileProcess(multipartRequest, category);
        map.put("fileList", fileList);
        
        // ✅ 업로드 완료 후 상품 목록으로 이동
        return new ModelAndView("redirect:/admin/listProducts.do");
    }
    
    // ✅ 파일 저장 처리 메서드 (카테고리별 폴더 생성 후 저장)
    private List<String> fileProcess(MultipartHttpServletRequest multipartRequest, String category) throws Exception {
        List<String> fileList = new ArrayList<String>();

        // ✅ 카테고리 폴더 경로 설정
        String uploadPath = CURR_IMAGE_REPO_PATH + "\\" + category;
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();  // 카테고리 폴더가 없으면 생성
        }
        
        // ✅ 업로드된 파일 처리
        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileParameterName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileParameterName);
            String originalFileName = mFile.getOriginalFilename();
            fileList.add(originalFileName);
            
            if (mFile.getSize() != 0) { // 파일이 존재하는 경우 저장
                File file = new File(uploadPath + "\\" + originalFileName);
                
                // 파일이 존재하지 않으면 부모 디렉토리 생성 후 새 파일 저장
                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                // ✅ 실제 파일 저장
                mFile.transferTo(file);
            }
        }
        return fileList;
    }
    
    
}