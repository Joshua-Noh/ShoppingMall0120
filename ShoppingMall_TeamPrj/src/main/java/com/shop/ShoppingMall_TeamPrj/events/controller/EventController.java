package com.shop.ShoppingMall_TeamPrj.events.controller;

import com.shop.ShoppingMall_TeamPrj.events.service.EventService;
import com.shop.ShoppingMall_TeamPrj.events.vo.EventVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * 모든 이벤트 목록을 조회하여 뷰에 전달합니다.
     * URL 예: /event/list.do
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String listEvents(Model model) {
        List<EventVO> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        // view resolver 또는 Tiles에 따라 "events/eventView"에 해당하는 JSP 호출
        return "events/eventView";
    }

    /**
     * 특정 이벤트 상세 정보를 조회합니다.
     * URL 예: /event/view.do?eventId=번호
     */
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public String viewEvent(int eventId, Model model) {
        EventVO event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        return "events/eventView";
    }

    /**
     * 새로운 이벤트를 생성합니다.
     * URL 예: /event/create.do (POST 방식)
     */
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String createEvent(EventVO event) {
        // EventVO의 프로퍼티는 스프링이 요청 파라미터로 자동 바인딩합니다.
        eventService.registerEvent(event);
        // 생성 후 이벤트 목록으로 리다이렉트
        return "redirect:/event/list.do";
    }

    /**
     * 기존 이벤트를 업데이트합니다.
     * URL 예: /event/update.do (POST 방식)
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String updateEvent(EventVO event) {
        eventService.updateEvent(event);
        return "redirect:/event/view.do?eventId=" + event.getEventId();
    }

    /**
     * 이벤트를 삭제합니다.
     * URL 예: /event/delete.do (POST 방식)
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String deleteEvent(int eventId) {
        eventService.removeEvent(eventId);
        return "redirect:/event/list.do";
    }
}
