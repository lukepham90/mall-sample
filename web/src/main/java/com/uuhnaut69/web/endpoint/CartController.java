package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.common.utils.PagingUtils;
import com.uuhnaut69.core.domain.model.Cart;
import com.uuhnaut69.core.mapper.CartMapper;
import com.uuhnaut69.core.payload.request.CartRequest;
import com.uuhnaut69.core.payload.response.CartResponse;
import com.uuhnaut69.core.service.cart.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.uuhnaut69.security.jwt.SecurityUtils.getCurrentUserId;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "Cart", value = "Endpoint Brand")
public class CartController {

  private final CartService cartService;

  private final CartMapper cartMapper;

  @ApiOperation(value = "Get Carts Endpoint", notes = "Admin endpoint")
  @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CART_URL)
  public GenericResponse getCartPageWithAdminRole(
      @RequestParam(value = "sort", defaultValue = "id") String sortBy,
      @RequestParam(value = "order", defaultValue = "desc") String order,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

    Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
    Page<Cart> carts = cartService.findAll(pageable);
    List<CartResponse> list = cartMapper.toListCartResponse(carts.getContent());
    return GenericResponse.builder().data(list).build();
  }

  @GetMapping(path = UrlConstants.CART_URL)
  @ApiOperation(value = "Get Carts Endpoint", notes = "User endpoint")
  public GenericResponse getCartPageWithUserRole(
      @RequestParam(value = "sort", defaultValue = "id") String sortBy,
      @RequestParam(value = "order", defaultValue = "desc") String order,
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
    Page<Cart> carts = cartService.findAllByUserId(pageable, getCurrentUserId().get());
    List<CartResponse> list = cartMapper.toListCartResponse(carts.getContent());
    return GenericResponse.builder().data(list).build();
  }

  @ApiOperation(value = "Get Cart's Detail", notes = "Admin endpoint")
  @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CART_URL + "/{cartId}")
  public GenericResponse getCartById(@PathVariable UUID cartId) {

    Cart cart = cartService.findById(cartId);
    CartResponse cartResponse = cartMapper.toCartResponse(cart);
    return GenericResponse.builder().data(cartResponse).build();
  }

  @ApiOperation(value = "Get Cart's Detail", notes = "User endpoint")
  @GetMapping(path = UrlConstants.CART_URL + "/{cartId}")
  public GenericResponse getCartByIdWithUserRole(@PathVariable UUID cartId) {
    Cart cart = cartService.findByIdAndUserId(cartId, getCurrentUserId().get());
    CartResponse cartResponse = cartMapper.toCartResponse(cart);
    return GenericResponse.builder().data(cartResponse).build();
  }

  @ApiOperation(value = "Create New Card Endpoint", notes = "Admin endpoint")
  @PostMapping(path = UrlConstants.CART_URL)
  public GenericResponse create(@RequestBody CartRequest cartRequest) {
    Cart cart = cartService.create(cartRequest, getCurrentUserId().get());
    CartResponse cartResponse = cartMapper.toCartResponse(cart);
    return GenericResponse.builder().data(cartResponse).build();
  }

  @ApiOperation(value = "Delete Card Endpoint", notes = "Admin endpoint")
  @DeleteMapping(path = UrlConstants.CART_URL + "/{cartId}")
  public GenericResponse delete(@PathVariable UUID cartId) {
    cartService.delete(cartId, getCurrentUserId().get());
    return new GenericResponse();
  }
}
