package com.alovi.webservice;


import com.alovi.common.Order;

public class OrderServiceImpl implements OrderService {

	@Override
	public int createOrder(String mobiPhone, Order order) {
		/*int orderId = 0;
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlCreateOrder(), mobiPhone);
		// String url =
		// ServiceConfiguration.getServiceConfiguration().getUrlCreateOrder();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<Order> requestEntity = new HttpEntity<Order>(order,
				requestHeaders);
		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<Integer> responseEntity = restTemplate.exchange(url,
					HttpMethod.PUT, requestEntity, Integer.class);
			orderId = responseEntity.getBody();
		} catch (Exception e) {
			Log.e("createOrder", e.getMessage());
		}
		return orderId;*/
		return 0;
	}

	@Override
	public String updateOrder(String mobilePhone, Order order) {
		/*String updateOrderResult = "";
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlUpdateOrder(), mobilePhone,
				order.OrderId);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<Order> requestEntity = new HttpEntity<Order>(order,
				requestHeaders);
		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,
					HttpMethod.PUT, requestEntity, String.class);
			updateOrderResult = responseEntity.getBody();
		} catch (Exception e) {
			Log.e("updateOrder", e.getMessage());
		}
		return updateOrderResult;*/
		return "";
	}

	@Override
	public Order getCurrentOrder(String mobilePhone, String digitalCode) {

		/*Order currentOrder = null;
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlGetCurrentOrder(),
				mobilePhone, digitalCode);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<Order> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, Order.class);
			currentOrder = responseEntity.getBody();
		} catch (Exception e) {
			Log.e("getCurrentOrder", e.getMessage());
		}
		return currentOrder;*/
		return null;
	}

	@Override
	public String fakePayOrder(String mobilePhone, int orderId) {
		/*String orderProcessResult = "";
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlFakePay(), mobilePhone,
				orderId);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, String.class);
			orderProcessResult = responseEntity.getBody();
		} catch (Exception e) {
			Log.e("fakePayOrder", e.getMessage());
		}
		return orderProcessResult;*/
		return "";
	}

	@Override
	public Order getOrder(int orderId) {
		/*Order currentOrder = null;
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlGetOrder(), orderId);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<Order> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, Order.class);
			currentOrder = responseEntity.getBody();
		} catch (Exception e) {
			Log.e("getOrder", e.getMessage());
		}
		return currentOrder;*/
		return null;
	}

	@Override
	public int getOrderRevision(int orderId) {
		/*int orderRevision = 1;
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlGetOrderRevision(), orderId);

		Log.d("getOrderRevision", url);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<Integer> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, Integer.class);
			orderRevision = responseEntity.getBody();
		} catch (Exception e) {
			Log.d("getOrderRevisionException", e.getMessage());
		}
		return orderRevision;*/
		return 0;
	}

	@Override
	public String getOrderState(int orderId) {
		/*String orderState = "";
		String url = String.format(ServiceConfiguration
				.getServiceConfiguration().getUrlGetOrderState(), orderId);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = RestTemplateFactory.getHttpRestTemplate();

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,
					HttpMethod.GET, requestEntity, String.class);
			orderState = responseEntity.getBody();
		} catch (Exception e) {
			Log.e("getOrderState", e.getMessage());
		}
		return orderState;*/
		return "";
	}

}
