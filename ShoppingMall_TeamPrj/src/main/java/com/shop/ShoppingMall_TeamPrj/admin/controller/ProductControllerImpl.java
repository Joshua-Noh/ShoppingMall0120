package com.shop.ShoppingMall_TeamPrj.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
    
    // FileDownloadController와 동일한 저장 경로 사용
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shoppingMall\\list_image";
    
    // 관리자 기능 접근 시, 로그인 상태 및 관리자 권한 체크
    private boolean isAdminLoggedIn(HttpServletRequest request) {
        System.out.println("[DEBUG] isAdminLoggedIn() 호출");
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] session == null -> 관리자 로그인 false");
            return false;
        }
        MemberVO member = (MemberVO) session.getAttribute("memberInfo");
        if (member == null) {
            System.out.println("[DEBUG] session의 memberInfo == null -> 관리자 로그인 false");
            return false;
        }
        boolean result = "ADMIN".equalsIgnoreCase(member.getRole());
        System.out.println("[DEBUG] 사용자 role: " + member.getRole() + ", isAdmin? " + result);
        return result;
    }
    
    @RequestMapping(value = "/admin/*Form.do", method = RequestMethod.GET)
    private ModelAndView form(@RequestParam(value = "result", required = false) String result,
                              @RequestParam(value = "action", required = false) String action,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] form() 호출");
        System.out.println("[DEBUG] 전달된 파라미터: result=" + result + ", action=" + action);

        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("[DEBUG] viewName: " + viewName);

        HttpSession session = request.getSession();
        System.out.println("[DEBUG] session에 action 설정: " + action);
        session.setAttribute("action", action);

        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        mav.setViewName(viewName);
        System.out.println("[DEBUG] ModelAndView 생성 - viewName: " + viewName + ", result: " + result);
        return mav;
    }
    
    @RequestMapping(value = "/admin/updateProductForm.do", method = RequestMethod.GET)
    private ModelAndView updateProductForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] updateProductForm() 호출");

        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("[DEBUG] viewName: " + viewName);

        String productId = request.getParameter("productId");
        System.out.println("[DEBUG] 전달된 productId: " + productId);

        ProductVO product = productservice.updateProductFormData(Integer.parseInt(productId));
        System.out.println("[DEBUG] productservice.updateProductFormData() 수행 결과: " + product);

        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.setViewName(viewName);
        System.out.println("[DEBUG] ModelAndView 생성 - viewName: " + viewName + ", product: " + product);
        return mav;
    }
    
    @Override
    @RequestMapping(value= "/admin/listProducts.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] listProducts() 호출");
        
        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        HttpSession session = request.getSession(false);
        MemberVO loginMember = (MemberVO) session.getAttribute("memberInfo");
        if (!"ADMIN".equalsIgnoreCase(loginMember.getRole())) {
            System.out.println("[DEBUG] 일반 유저가 접근 -> 메인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/main/main.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("[DEBUG] viewName: " + viewName);

        List productList = productservice.listProducts();
        System.out.println("[DEBUG] productList 조회 결과: " + productList);

        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("productList", productList);
        System.out.println("[DEBUG] ModelAndView 생성 - viewName: " + viewName + ", productList.size: " + productList.size());
        return mav;
    }

    // 제품 등록 후 생성된 product_id를 받아 이미지 업로드 폼으로 전달
    @Override
    @RequestMapping(value = "/admin/addProduct.do", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("product") ProductVO product,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] addProduct() 호출");
        System.out.println("[DEBUG] 전달된 product: " + product);

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        int result = productservice.addproduct(product);
        System.out.println("[DEBUG] productservice.addproduct() 결과: " + result);

        // 제품 등록 후, MyBatis의 useGeneratedKeys 속성 덕분에 productId가 세팅됨
        int productId = product.getProductId();
        System.out.println("[DEBUG] 생성된 product_id: " + productId);

        // 생성된 product_id를 쿼리 파라미터로 이미지 업로드 폼에 전달
        ModelAndView mav = new ModelAndView("redirect:/admin/imageUploadForm.do?product_id=" + productId);
        System.out.println("[DEBUG] ModelAndView 생성 - redirect:/admin/imageUploadForm.do?product_id=" + productId);
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/removeProduct.do", method = RequestMethod.GET)
    public ModelAndView removeProduct(@RequestParam("productId") int id, 
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] removeProduct() 호출");
        System.out.println("[DEBUG] 전달된 productId: " + id);

        request.setCharacterEncoding("utf-8");
        
        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        productservice.removeProduct(id);
        System.out.println("[DEBUG] productservice.removeProduct() 수행 완료");

        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        System.out.println("[DEBUG] ModelAndView 생성 - redirect:/admin/listProducts.do");
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] updateProduct() 호출");
        System.out.println("[DEBUG] 전달된 product: " + product);

        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        int result = productservice.updateProduct(product);
        System.out.println("[DEBUG] productservice.updateProduct() 결과: " + result);

        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        if (result > 0) {
            mav.addObject("message", "수정 성공");
            System.out.println("[DEBUG] 수정 성공 -> message: '수정 성공'");
        } else {
            mav.addObject("message", "수정 실패");
            System.out.println("[DEBUG] 수정 실패 -> message: '수정 실패'");
        }
        System.out.println("[DEBUG] ModelAndView 생성 - redirect:/admin/listProducts.do");
        return mav;
    }
    
    @RequestMapping(value="/admin/imageUpload.do")
    public String form() {
        System.out.println("[DEBUG] imageUpload.do -> form() 호출");
        return "imageUploadForm";  // imageUploadForm.jsp
    }
    
    // 파일 업로드 시, product_id를 폴더명으로 사용하고 파일명을 "product_image_{product_id}.jpg"로 변경하며,
    // 동시에 detail_image 테이블에 해당 정보를 기록합니다.
    @RequestMapping(value="/admin/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response,
                               @RequestParam("product_id") String product_id) throws Exception {
        System.out.println("[DEBUG] upload() 호출");
        System.out.println("[DEBUG] 전달된 product_id: " + product_id);

        multipartRequest.setCharacterEncoding("utf-8");

        if (!isAdminLoggedIn(multipartRequest)) {
            System.out.println("[DEBUG] 관리자 로그인이 아님 -> 로그인 페이지로 리다이렉트");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        List<String> fileList = fileProcess(multipartRequest, product_id);
        System.out.println("[DEBUG] fileProcess() 결과 fileList.size: " + fileList.size());
        
        // 파일 업로드가 완료된 후, detail_image 테이블에 저장할 정보를 등록합니다.
        // fileType은 "main_image"로 고정합니다.
        for(String fileName : fileList) {
            int insertResult = productservice.insertDetailImage(Integer.parseInt(product_id), fileName, "main_image");
            System.out.println("[DEBUG] detail_image insert 결과: " + insertResult);
        }

        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        System.out.println("[DEBUG] 파일 업로드 및 detail_image 기록 후 listProducts로 리다이렉트");
        return mav;
    }
    
    private List<String> fileProcess(MultipartHttpServletRequest multipartRequest, String product_id) throws Exception {
        System.out.println("[DEBUG] fileProcess() 호출");
        System.out.println("[DEBUG] product_id: " + product_id);

        List<String> fileList = new ArrayList<String>();
        // 업로드 경로: 기본 경로 + File.separator + product_id
        String uploadPath = CURR_IMAGE_REPO_PATH + File.separator + product_id;
        System.out.println("[DEBUG] uploadPath: " + uploadPath);

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            boolean mkResult = dir.mkdirs();
            System.out.println("[DEBUG] 디렉토리 생성 여부: " + mkResult);
        } else {
            System.out.println("[DEBUG] 이미 존재하는 디렉토리: " + uploadPath);
        }

        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileParameterName = fileNames.next();
            System.out.println("[DEBUG] 현재 파일 파라미터명: " + fileParameterName);

            MultipartFile mFile = multipartRequest.getFile(fileParameterName);
            if (mFile == null) {
                System.out.println("[DEBUG] MultipartFile이 null입니다 -> 스킵");
                continue;
            }
            System.out.println("[DEBUG] 실제 업로드된 파일명: " + mFile.getOriginalFilename() 
                               + ", 크기: " + mFile.getSize());

            // 새 파일명: product_image_{product_id}.jpg
            String newFileName = "product_image_" + product_id + ".jpg";
            fileList.add(newFileName);
            System.out.println("[DEBUG] 저장될 파일명: " + newFileName);

            if (mFile.getSize() != 0) {
                File file = new File(uploadPath + File.separator + newFileName);
                try {
                    // 상위 디렉토리 확인: 없으면 생성
                    if (!file.getParentFile().exists()) {
                        if (file.getParentFile().mkdirs()) {
                            System.out.println("[DEBUG] 상위 디렉토리 생성 완료");
                        }
                    }
                    // 파일이 존재하지 않으면 새 파일 생성 (없어도 transferTo에서 생성 가능)
                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println("[DEBUG] 새 파일 생성 완료: " + file.getAbsolutePath());
                    }
                    System.out.println("[DEBUG] 파일 저장 시도: " + file.getAbsolutePath());
                    mFile.transferTo(file);
                    System.out.println("[DEBUG] 파일 저장 성공");
                } catch (Exception e) {
                    System.err.println("[DEBUG] 파일 저장 중 예외 발생: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("[DEBUG] 파일 크기가 0입니다 -> 업로드 스킵");
            }
        }
        System.out.println("[DEBUG] 최종 fileList: " + fileList);
        return fileList;
    }
}
