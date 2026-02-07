package kg.attractor.javasharehub.controller;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.javasharehub.common.Utilities;
import kg.attractor.javasharehub.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam Long categoryId,
                         @RequestParam String status,
                         Principal principal) {
        fileService.upload(file, principal.getName(), categoryId, status);
        return "redirect:/profile";
    }

    @GetMapping("/download-public")
    public ResponseEntity<?> downloadPublic(@RequestParam Long id) {
        return fileService.processPublicDownload(id);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadPrivate(@RequestParam String token) {
        return fileService.processDownloadByToken(token);
    }

    @PostMapping("/generate-link")
    public String generateLink(@RequestParam Long fileId,
                               HttpServletRequest request,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {
        try {
            String path = fileService.generatePrivateLink(fileId, principal.getName());

            String baseUrl = Utilities.getSiteUrl(request);
            String fullUrl = baseUrl + "/files" + path;
            redirectAttributes.addFlashAttribute("generatedLink", fullUrl);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }
}
