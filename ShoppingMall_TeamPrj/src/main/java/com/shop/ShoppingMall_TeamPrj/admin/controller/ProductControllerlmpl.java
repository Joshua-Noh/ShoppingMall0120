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
       
        
        return new ModelAndView("redirect:/admin/listProducts.do");
    }
    @Override
    @RequestMapping(value = "/admin/removeProduct.do", method = RequestMethod.GET)
    public ModelAndView removeProduct(@RequestParam("productId") int id, 
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        // UTF-8 ���ڵ� ���� (�̹� ��û�� �����Ǿ� ������ ���� ����)
        request.setCharacterEncoding("utf-8");
        // ���� ȣ���Ͽ� ȸ�� ����
        productservice.removeProduct(id);
        // ���� �� ȸ�� ��� �������� �����̷�Ʈ
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        return mav;
    }
    
    @Override
    @RequestMapping(value = "/admin/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(@ModelAttribute("product") ProductVO product, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int result = productservice.updateProduct(product);
        ModelAndView mav = new ModelAndView("redirect:/admin/listProducts.do");
        if (result > 0) {
            mav.addObject("message", "ȸ�� ���� ���� ����");
        } else {
            mav.addObject("message", "ȸ�� ���� ���� ����");
        }
        return mav;
    }

    
}