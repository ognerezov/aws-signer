package net.okhotnikov.aws_signer.web;

import net.okhotnikov.aws_signer.config.CloudFrontConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PubController {
    @GetMapping("distribution")
    public String getDistribution(){
        return CloudFrontConfig.DEFAULT_DISTRIBUTION;
    }
}
