package net.okhotnikov.aws_signer.web;

import net.okhotnikov.aws_signer.api.ListHolder;
import net.okhotnikov.aws_signer.service.CloudFrontService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sign")
public class SignController {

    private final CloudFrontService cloudFrontService;

    public SignController(CloudFrontService cloudFrontService) {
        this.cloudFrontService = cloudFrontService;
    }

    @GetMapping("link/{id:.+}")
    public String sign(@PathVariable String id){
        return cloudFrontService.getSignedUrl(id);
    }

    @PostMapping("links")
    public ListHolder<String> getLinks(@RequestBody ListHolder<String> request){
        return new ListHolder<>(cloudFrontService.getSigned(request.list));
    }
}
