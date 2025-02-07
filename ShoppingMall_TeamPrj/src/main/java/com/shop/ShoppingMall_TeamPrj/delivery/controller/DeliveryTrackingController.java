package com.shop.ShoppingMall_TeamPrj.delivery.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shop.ShoppingMall_TeamPrj.delivery.service.DeliveryTrackingService;

@Controller
@RequestMapping("/delivery")
public class DeliveryTrackingController {

    @Autowired
    private DeliveryTrackingService deliveryTrackingService;
    
    /**
     * 배송조회 API를 호출하여 결과를 JSP에 전달.
     * carrierId와 trackId는 배송조회에 필요한 파라미터입니다.
     */
    @RequestMapping(value="/track.do", method=RequestMethod.GET)
    public ModelAndView track(@RequestParam("carrierId") String carrierId,
                              @RequestParam("trackId") String trackId) {
        JSONObject result = deliveryTrackingService.callLogAPI(carrierId, trackId);
        
        ModelAndView mav = new ModelAndView("delivery/trackResult"); // /WEB-INF/views/delivery/trackResult.jsp
        mav.addObject("trackingResult", result.toString());
        return mav;
    }
}
