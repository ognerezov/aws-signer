package net.okhotnikov.aws_signer.web;

import net.okhotnikov.aws_signer.service.CloudFrontService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sign")
public class SignController {

    private final CloudFrontService cloudFrontService;

    public SignController(CloudFrontService cloudFrontService) {
        this.cloudFrontService = cloudFrontService;
    }

    @GetMapping("link/{id:.+}")
    public String sign(@PathVariable String id){
        return cloudFrontService.getSignedUrl("russian/"+id);
    }
}
