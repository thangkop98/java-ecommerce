package vn.thangdev.ecommerce.controller.enduser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageNotFoundController {
    @GetMapping("/not-found")
    public String redirectToNotFoundPage() {
        return "user/404";
    }
    
}
