package com.model2.mvc.web.purchase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;


//==> 회원관리 Controller
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	//setter Method 구현 않음
		
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseRestController(){
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
	
	
	
	
	@RequestMapping(value="json/addPurchase/{prodNo}", method=RequestMethod.POST)
	public void addPurchase( @RequestBody Purchase purchase, @PathVariable int prodNo, 
			Model model) throws Exception {
		
		System.out.println("/purchase/addPurchase : POST");
		//Business Logic
//		User user = (User)session.getAttribute("user");
		Product product = productService.getProduct(prodNo);
		purchase.setPurchaseProd(product);
//		purchase.setBuyer(user);
		
		purchaseService.addPurchase(purchase);
		
		model.addAttribute("purchase", purchase);
		
	}
	
	

	@RequestMapping(value="json/getPurchase/{tranNo}", method=RequestMethod.GET)
	public void getPurchase( @PathVariable int tranNo, Model model) throws Exception {
		
		System.out.println("/purchase/getPurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
		
	}
	
	

	@RequestMapping(value="json/updatePurchase", method=RequestMethod.POST)
	public void updatePurchase( @RequestBody Purchase purchase, Model model) throws Exception{
		
		System.out.println("/purchase/updatePurchase : POST");
		//Business Logic
		System.out.println("purchase ::: " + purchase);
		purchaseService.updatePurchase(purchase);
		
		model.addAttribute("purchase", purchase);
		
	}
	
	
	@RequestMapping(value="json/listPurchase/{userId}")
	public void listPurchase(@RequestBody Search search, @PathVariable String userId,
							Model model) throws Exception{
		
		System.out.println("/purchase/listPurchase");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search, userId);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
	}
}