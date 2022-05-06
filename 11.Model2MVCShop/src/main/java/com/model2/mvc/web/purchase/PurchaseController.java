package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;


//==> �쉶�썝愿�由� Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	//setter Method 援ы쁽 �븡�쓬
		
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 李몄“ �븷寃�
	//==> �븘�옒�쓽 �몢媛쒕�� 二쇱꽍�쓣 ���뼱 �쓽誘몃�� �솗�씤 �븷寃�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchase(@RequestParam("prodNo") int prodNo) throws Exception {

		System.out.println("/purchase/addPurchase : GET");
		Product product = productService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase, @RequestParam("prodNo") int prodNo, 
								HttpSession session) throws Exception {

		System.out.println("/purchase/addPurchase : POST");
		//Business Logic
		User user = (User)session.getAttribute("user");
		Product product = productService.getProduct(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="getPurchase", method=RequestMethod.GET)
	public ModelAndView getPurchase( @RequestParam("tranNo") int tranNo) throws Exception {
		
		System.out.println("/purchase/getPurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 怨� View �뿰寃�
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("vo", purchase);
		
		return modelAndView;
	}

	
	@RequestMapping(value="updatePurchase", method=RequestMethod.GET)
	public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo) throws Exception{

		System.out.println("/purchase/updateUser : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// Model 怨� View �뿰寃�
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("vo", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public ModelAndView updatePurchase( @ModelAttribute("purchase") Purchase purchase) throws Exception{

		System.out.println("updateUser : POST 시작");
		//Business Logic
		System.out.println("purchase ::: " + purchase);
		purchaseService.updatePurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/listPurchase");
		
		return modelAndView;
	}

	@RequestMapping(value="updateTranCode", method=RequestMethod.GET)
	public ModelAndView updateTranCode(@RequestParam("prodNo") int prodNo, 
								@RequestParam("tranCode") String tranCode) throws Exception{
		
		System.out.println("updateTranCode : GET 시작");

		Product product = new Product();
		product.setProdNo(prodNo);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/listPurchase");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updateTranCodeByProd", method=RequestMethod.GET)
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo") int prodNo, 
			@RequestParam("tranCode") String tranCode) throws Exception{
		
		System.out.println("updateTranCodeByProd : GET 시작");
		
		Product product = new Product();
		product.setProdNo(prodNo);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/product/listProduct?menu=manage");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception{
		
		System.out.println("/listPurchase 시작");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		User buyer = (User)session.getAttribute("user");
		
		// Business logic �닔�뻾
		Map<String , Object> map=purchaseService.getPurchaseList(search, buyer.getUserId());
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 怨� View �뿰寃�
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		return modelAndView;
	}
}