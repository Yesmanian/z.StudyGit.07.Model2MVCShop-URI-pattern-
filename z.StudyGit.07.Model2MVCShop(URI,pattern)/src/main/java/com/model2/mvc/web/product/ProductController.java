package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public	ModelAndView addProduct(@ModelAttribute("product")Product product,@RequestParam("file")MultipartFile multipartFile) throws Exception {
		
		System.out.println("/addProduct start");
		
		System.out.println("[addProduct]  "+product);
		
		//Business Logic
		String originalManuDate = product.getManuDate();
		String[] splitManuData = originalManuDate.split("-");
		String manuDate = String.join("", splitManuData);
		product.setManuDate(manuDate);
		
		//fileadd
		product.setFileName(multipartFile.getOriginalFilename());
		
		productService.addProduct(product);
		
		//file
		File targetFile = new File("C:\\Users\\doyeon\\git\\rz.StudyGit.07.Model2MVCShop(URI,pattern)\\z.StudyGit.07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles"
						,multipartFile.getOriginalFilename());
		multipartFile.transferTo(targetFile);
		
		System.out.println(targetFile.getName());
		
		
		
		//modelandview
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/addProduct.jsp");
		modelAndView.addObject("vo", product);
		
		System.out.println("/addProduct end");
		
		return modelAndView;
		
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value = "getProduct")
	public ModelAndView getProduct(HttpServletRequest request,HttpServletResponse response) throws  Exception {
		
		System.out.println("/getProduct Start...");
		
		//Business Logic
		Product product = productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		String tranCode = request.getParameter("tranCode");
		String role     = request.getParameter("menu");
		//Cookie
		
		String history = String.valueOf(product.getProdNo())+",";
		
		Cookie[] cookie = request.getCookies();
		
		for(Cookie c : cookie) {
			if(c.getName().equals("history")) {
				String temp = c.getValue();
				c.setValue(history+temp);
				response.addCookie(c);
			}else {
				response.addCookie(new Cookie("history", history));
			}
		}//cookie 끝
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("vo", product);
		
		if(role == null) {
			System.out.println("go to getProduct.jsp");
			modelAndView.setViewName("forward:/product/getProduct.jsp");
			return modelAndView;
		}
		
		if(role.equals("manage") && (request.getParameter("tranCode").equals("1") || request.getParameter("tranCode")=="")) {
			//수정하는것
			System.out.println("go to manager ===> updateProductView.jsp");
			modelAndView.setViewName("forward:/product/updateProductView.jsp");
		}else{			
			//상세보기하는것
			System.out.println("go to getProduct.jsp");
			modelAndView.setViewName("forward:/product/getProduct.jsp");			
		}
		
		
		System.out.println("/getProduct End...");
		return modelAndView;
	}
	
	
	
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value = "listProduct")
	public ModelAndView listProduct(@ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct start..");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic
		Map<String ,  Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		
		
		//role setting
		String role = request.getParameter("menu");
		request.setAttribute("menu", role);
		
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/listProduct.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);

		System.out.println("/listProduct end..");
		return modelAndView;
	}
	
	
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value = "updateProduct" , method = RequestMethod.POST)
	public ModelAndView updateProduct(@ModelAttribute("product")Product product) throws Exception {
		
		System.out.println("/updateProduct Start...");
		
		//Business Logic
		System.out.println(product.getManuDate());
		String originalManuDate = product.getManuDate();
		String[] splitManuData = originalManuDate.split("-");
		String manuDate = String.join("", splitManuData);
		product.setManuDate(manuDate);
		
		
		System.out.println("======"+product);
		
		productService.updateProduct(product);
		
		
		//modelandview
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/getProduct?prodNo="+product.getProdNo());
		modelAndView.addObject("vo", product);
		
		
		
		
		System.out.println("/updateProduct End...");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "listPicture")
	public ModelAndView listPicture() {
		
		System.out.println("/listPicture.do Start....");
		
		
		
		//ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/listPicture.jsp");
		
		
		
		
		System.out.println("/listPicture.do End....");
		return modelAndView;
	}
	
	@RequestMapping(value = "addPicture")
	public ModelAndView addPicture(@RequestParam("file")List<MultipartFile> multipartFiles) throws IllegalStateException, IOException {
		
		System.out.println("/addPicture.do Start....");
		
		//Business Logic
		//fileadd
//		product.setFileName(multipartFile.getOriginalFilename());
//		
//		productService.addProduct(product);
		
		//file
		for(MultipartFile multipartFile : multipartFiles) {
		File targetFile = new File("C:\\Users\\doyeon\\git\\rz.StudyGit.07.Model2MVCShop(URI,pattern)\\z.StudyGit.07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles"
						,multipartFile.getOriginalFilename());
		multipartFile.transferTo(targetFile);
		
		System.out.println(targetFile.getName());
		}
		//ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/listPicture.jsp");
		
		
		
		
		System.out.println("/addPicture.do End....");
		return modelAndView;
	}
	
	
	
	
	
	
//	@RequestMapping("/updateProductView")
	
	
	
	
}
