package vn_hcmute.crud_springboost3.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.ui.Model;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn_hcmute.crud_springboost3.Entity.Category;
import vn_hcmute.crud_springboost3.Services.ICategoryService;
import vn_hcmute.crud_springboost3.Services.IStorageService;


import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(path = "/admin/categories")

public class CategoryAdminController {

    @Autowired
    private ICategoryService categoryService;

    @Qualifier("fileSystemStorageService")
    @Autowired
    private IStorageService storageService;

    // Hiển thị tất cả danh mục
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> list = categoryService.findAll();
        model.addAttribute("cate", list);
        return "admin/category/home";
    }
    @GetMapping("/addCategory")
    public String signup(Category category, ModelMap modelMap){
        modelMap.addAttribute("category", category);
        return "admin/category/add-category";
    }
    @GetMapping("/home")
    public String home(Model model) {
        List<Category> list = categoryService.findAll();
        model.addAttribute("cate", list);
        return "admin/category/searchpaginated";
    }
    // Thêm hoặc cập nhật danh mục dựa trên sự tồn tại của categoryId
    @PostMapping("/add")
    public String addCategory(@Validated @RequestParam("categoryName") String categoryName,
                              @RequestParam("icon") MultipartFile icon, Model model) {
        if (categoryService.findByCategoryName(categoryName).isPresent()) {
            return "Danh mục đã tồn tại";
        }
        Category category = new Category();
        category.setCategoryName(categoryName);
        if (!icon.isEmpty()) {
            String filename = storageService.getStorageFilename(icon, UUID.randomUUID().toString());
            category.setIcon(filename);
            storageService.store(icon, filename);
        }
        categoryService.save(category);
        return "redirect:/admin/categories/searchpaginated";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long categoryId, Model model) {
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        model.addAttribute("category", category);
        return "admin/category/edit_category";
    }



    // Xóa danh mục
    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") long categoryId, RedirectAttributes redirectAttributes) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy danh mục");
            return "redirect:/admin/categories/searchpaginated";
        }
        categoryService.delete(optCategory.get());
        redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục thành công");
        return "redirect:/admin/categories/searchpaginated";
    }


    @RequestMapping("/searchpaginated")
    public String search(ModelMap model,
                         @RequestParam(name = "categoryName", required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int count = (int)categoryService.count();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryName"));
        Page<Category> resultPage = null;

        if (StringUtils.hasText(name)) {
            resultPage = categoryService.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
            resultPage = categoryService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();


        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > count) {
                if (end == totalPages)
                    start = end - count;
                else if (start == 1)
                    end = start + count;
            }

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("categoryPage", resultPage);
        return "admin/category/searchpaginated";
    }



}
