package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService	productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prodNo")int prodNo) throws Exception {
		
		System.out.println("/addPurchaseView.do Start...");
		

		
		System.out.println(prodNo);
		Product product = productService.getProduct(prodNo);
		System.out.println(product);
		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("vo", product);
		

		
		System.out.println("/addPurchaseView.do End...");
		return modelAndView;
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(HttpSession session,@ModelAttribute("purchase")Purchase purchase,HttpServletRequest request) throws  Exception {
		
		System.out.println("/addPurchase.do Start..");

		User user = (User)session.getAttribute("user");
		purchase.setBuyer(user);
		purchase.setPurchaseProd(productService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		
		//Business Logic
		purchaseService.addPurchase(purchase);
		
		
		
		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("vo", purchase);
		
		
		
		
		
		System.out.println("/addPurchase.do End..");
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search,HttpSession session) throws Exception {
		
		System.out.println("/listPurchase.do Start..");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		Map<String ,  Object> map = purchaseService.getPurchaseList(search, buyerId);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		
		
		System.out.println("/listPurchase.do End..");
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo")int tranNo,@RequestParam("tranCode")String tranCode) throws Exception {
	
		System.out.println("/getPurchase.do Start...");
		
		//business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("vo", purchase);
		modelAndView.addObject("tranCode", tranCode);

		
		System.out.println("/getPurchase.do End...");
		return modelAndView;
	}
	
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo")int tranNo) throws Exception {
		
		System.out.println("/updatePurchaseView.do Start....");
		
		//business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("vo", purchase);		
		

		System.out.println("/updatePurchaseView.do End....");
		return modelAndView;
	}
	
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase")Purchase purchase) throws Exception {
		
		System.out.println("/updatePurchase.do Start....");
		
//		Purchase purchaseByTranNo = purchaseService.getPurchase(tranNo);
//		purchaseByTranNo.setTranNo(tranNo);
		
		System.out.println(purchase.getTranNo());
		
		
		
		//business Logic
		purchaseService.updatePurchase(purchase);
		purchase = purchaseService.getPurchase(purchase.getTranNo());
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("vo", purchase);		


		System.out.println("/updatePurchase.do End....");
		
		return modelAndView;
		
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@RequestParam("tranNo")int tranNo,
			@RequestParam("tranCode")String tranCode) throws Exception {
		
		System.out.println("/updateTranCode.do Start....");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		purchaseService.updateTranCode(purchase, tranCode);
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/listPurchase.do");
		//modelAndView.addObject("vo", purchase);		
		

		System.out.println("/updateTranCode.do End....");
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo")int prodNo,
			@RequestParam("tranCode")String tranCode) throws Exception {
		
		System.out.println("/updateTranCodeByProd.do Start...");
		
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		purchaseService.updateTranCode(purchase, tranCode);		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/listProduct.do?menu=manage");

		System.out.println("/updateTranCodeByProd.do End...");
		return modelAndView;
	}
	
}
