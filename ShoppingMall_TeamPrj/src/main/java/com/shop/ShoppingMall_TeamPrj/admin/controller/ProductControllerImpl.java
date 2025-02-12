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
    
    // FileDownloadController�� ������ ���� ��� ���
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shoppingMall\\list_image";
    
    // ������ ��� ���� ��, �α��� ���� �� ������ ���� üũ
    private boolean isAdminLoggedIn(HttpServletRequest request) {
        System.out.println("[DEBUG] isAdminLoggedIn() ȣ��");
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("[DEBUG] session == null -> ������ �α��� false");
            return false;
        }
        MemberVO member = (MemberVO) session.getAttribute("memberInfo");
        if (member == null) {
            System.out.println("[DEBUG] session�� memberInfo == null -> ������ �α��� false");
            return false;
        }
        boolean result = "ADMIN".equalsIgnoreCase(member.getRole());
        System.out.println("[DEBUG] ����� role: " + member.getRole() + ", isAdmin? " + result);
        return result;
    }
    
    @RequestMapping(value = "/admin/*Form.do", method = RequestMethod.GET)
    private ModelAndView form(@RequestParam(value = "result", required = false) String result,
                              @RequestParam(value = "action", required = false) String action,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] form() ȣ��");
        System.out.println("[DEBUG] ���޵� �Ķ����: result=" + result + ", action=" + action);

        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("[DEBUG] viewName: " + viewName);

        HttpSession session = request.getSession();
        System.out.println("[DEBUG] session�� action ����: " + action);
        session.setAttribute("action", action);

        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        mav.setViewName(viewName);
        System.out.println("[DEBUG] ModelAndView ���� - viewName: " + viewName + ", result: " + result);
        return mav;
    }
    
    @RequestMapping(value = "/admin/updateProductForm.do", method = RequestMethod.GET)
    private ModelAndView updateProductForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] updateProductForm() ȣ��");

        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("[DEBUG] viewName: " + viewName);

        String productId = request.getParameter("productId");
        System.out.println("[DEBUG] ���޵� productId: " + productId);

        ProductVO product = productservice.updateProductFormData(Integer.parseInt(productId));
        System.out.println("[DEBUG] productservice.updateProductFormData() ���� ���: " + product);

        ModelAndView mav = new ModelAndView();
        mav.addObject("product", product);
        mav.setViewName(viewName);
        System.out.println("[DEBUG] ModelAndView ���� - viewName: " + viewName + ", product: " + product);
        return mav;
    }
    
    @Override
    @RequestMapping(value= "/admin/listProducts.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] listProducts() ȣ��");
        
        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        HttpSession session = request.getSession(false);
        MemberVO loginMember = (MemberVO) session.getAttribute("memberInfo");
        if (!"ADMIN".equalsIgnoreCase(loginMember.getRole())) {
            System.out.println("[DEBUG] �Ϲ� ������ ���� -> ���� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/main/main.do");
        }
        
        String viewName = (String) request.getAttribute("viewName");
        System.out.println("[DEBUG] viewName: " + viewName);

        List productList = productservice.listProducts();
        System.out.println("[DEBUG] productList ��ȸ ���: " + productList);

        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("productList", productList);
        System.out.println("[DEBUG] ModelAndView ���� - viewName: " + viewName + ", productList.size: " + productList.size());
        return mav;
    }

    // ��ǰ ��� �� ������ product_id�� �޾� �̹��� ���ε� ������ ����
    @Override
    @RequestMapping(value = "/admin/addProduct.do", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("product") ProductVO product,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] addProduct() ȣ��");
        System.out.println("[DEBUG] ���޵� product: " + product);

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        int result = productservice.addproduct(product);
        System.out.println("[DEBUG] productservice.addproduct() ���: " + result);

        // ��ǰ ��� ��, MyBatis�� useGeneratedKeys �Ӽ� ���п� productId�� ���õ�
        int productId = product.getProductId();
        System.out.println("[DEBUG] ������ product_id: " + productId);

        // ������ product_id�� ���� �Ķ���ͷ� �̹��� ���ε� ���� ����
        ModelAndView mav = new ModelAndView("redirect:/admin/imageUploadForm.do?product_id=" + productId);
        System.out.println("[DEBUG] ModelAndView ���� - redirect:/admin/imageUploadForm.do?product_id=" + productId);
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/removeProduct.do", method = RequestMethod.GET)
    public ModelAndView removeProduct(@RequestParam("productId") int id, 
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] removeProduct() ȣ��");
        System.out.println("[DEBUG] ���޵� productId: " + id);

        request.setCharacterEncoding("utf-8");
        
        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        productservice.removeProduct(id);
        System.out.println("[DEBUG] productservice.removeProduct() ���� �Ϸ�");

        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        System.out.println("[DEBUG] ModelAndView ���� - redirect:/admin/listProducts.do");
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("[DEBUG] updateProduct() ȣ��");
        System.out.println("[DEBUG] ���޵� product: " + product);

        if (!isAdminLoggedIn(request)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }

        int result = productservice.updateProduct(product);
        System.out.println("[DEBUG] productservice.updateProduct() ���: " + result);

        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        if (result > 0) {
            mav.addObject("message", "���� ����");
            System.out.println("[DEBUG] ���� ���� -> message: '���� ����'");
        } else {
            mav.addObject("message", "���� ����");
            System.out.println("[DEBUG] ���� ���� -> message: '���� ����'");
        }
        System.out.println("[DEBUG] ModelAndView ���� - redirect:/admin/listProducts.do");
        return mav;
    }
    
    @RequestMapping(value="/admin/imageUpload.do")
    public String form() {
        System.out.println("[DEBUG] imageUpload.do -> form() ȣ��");
        return "imageUploadForm";  // imageUploadForm.jsp
    }
    
    // ���� ���ε� ��, product_id�� ���������� ����ϰ� ���ϸ��� "product_image_{product_id}.jpg"�� �����ϸ�,
    // ���ÿ� detail_image ���̺� �ش� ������ ����մϴ�.
    @RequestMapping(value="/admin/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response,
                               @RequestParam("product_id") String product_id) throws Exception {
        System.out.println("[DEBUG] upload() ȣ��");
        System.out.println("[DEBUG] ���޵� product_id: " + product_id);

        multipartRequest.setCharacterEncoding("utf-8");

        if (!isAdminLoggedIn(multipartRequest)) {
            System.out.println("[DEBUG] ������ �α����� �ƴ� -> �α��� �������� �����̷�Ʈ");
            return new ModelAndView("redirect:/member/loginForm.do");
        }
        
        List<String> fileList = fileProcess(multipartRequest, product_id);
        System.out.println("[DEBUG] fileProcess() ��� fileList.size: " + fileList.size());
        
        // ���� ���ε尡 �Ϸ�� ��, detail_image ���̺� ������ ������ ����մϴ�.
        // fileType�� "main_image"�� �����մϴ�.
        for(String fileName : fileList) {
            int insertResult = productservice.insertDetailImage(Integer.parseInt(product_id), fileName, "main_image");
            System.out.println("[DEBUG] detail_image insert ���: " + insertResult);
        }

        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        System.out.println("[DEBUG] ���� ���ε� �� detail_image ��� �� listProducts�� �����̷�Ʈ");
        return mav;
    }
    
    private List<String> fileProcess(MultipartHttpServletRequest multipartRequest, String product_id) throws Exception {
        System.out.println("[DEBUG] fileProcess() ȣ��");
        System.out.println("[DEBUG] product_id: " + product_id);

        List<String> fileList = new ArrayList<String>();
        // ���ε� ���: �⺻ ��� + File.separator + product_id
        String uploadPath = CURR_IMAGE_REPO_PATH + File.separator + product_id;
        System.out.println("[DEBUG] uploadPath: " + uploadPath);

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            boolean mkResult = dir.mkdirs();
            System.out.println("[DEBUG] ���丮 ���� ����: " + mkResult);
        } else {
            System.out.println("[DEBUG] �̹� �����ϴ� ���丮: " + uploadPath);
        }

        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileParameterName = fileNames.next();
            System.out.println("[DEBUG] ���� ���� �Ķ���͸�: " + fileParameterName);

            MultipartFile mFile = multipartRequest.getFile(fileParameterName);
            if (mFile == null) {
                System.out.println("[DEBUG] MultipartFile�� null�Դϴ� -> ��ŵ");
                continue;
            }
            System.out.println("[DEBUG] ���� ���ε�� ���ϸ�: " + mFile.getOriginalFilename() 
                               + ", ũ��: " + mFile.getSize());

            // �� ���ϸ�: product_image_{product_id}.jpg
            String newFileName = "product_image_" + product_id + ".jpg";
            fileList.add(newFileName);
            System.out.println("[DEBUG] ����� ���ϸ�: " + newFileName);

            if (mFile.getSize() != 0) {
                File file = new File(uploadPath + File.separator + newFileName);
                try {
                    // ���� ���丮 Ȯ��: ������ ����
                    if (!file.getParentFile().exists()) {
                        if (file.getParentFile().mkdirs()) {
                            System.out.println("[DEBUG] ���� ���丮 ���� �Ϸ�");
                        }
                    }
                    // ������ �������� ������ �� ���� ���� (��� transferTo���� ���� ����)
                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println("[DEBUG] �� ���� ���� �Ϸ�: " + file.getAbsolutePath());
                    }
                    System.out.println("[DEBUG] ���� ���� �õ�: " + file.getAbsolutePath());
                    mFile.transferTo(file);
                    System.out.println("[DEBUG] ���� ���� ����");
                } catch (Exception e) {
                    System.err.println("[DEBUG] ���� ���� �� ���� �߻�: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("[DEBUG] ���� ũ�Ⱑ 0�Դϴ� -> ���ε� ��ŵ");
            }
        }
        System.out.println("[DEBUG] ���� fileList: " + fileList);
        return fileList;
    }
}
