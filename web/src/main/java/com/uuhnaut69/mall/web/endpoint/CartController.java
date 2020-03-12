package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.core.utils.PagingUtils;
import com.uuhnaut69.mall.domain.model.Cart;
import com.uuhnaut69.mall.mapper.CartMapper;
import com.uuhnaut69.mall.payload.request.CartRequest;
import com.uuhnaut69.mall.payload.response.CartResponse;
import com.uuhnaut69.mall.service.cart.CartService;
import com.uuhnaut69.security.user.CurrentUser;
import com.uuhnaut69.security.user.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    /**
     * Get cart page with admin role
     *
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @return GenericResponse
     */
    @ApiOperation(value = "Get Carts Endpoint", notes = "Admin endpoint")
    @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CART_URL)
    public GenericResponse getCartPageWithAdminRole(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                                    @RequestParam(value = "order", defaultValue = "desc") String order,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Cart> carts = cartService.findAll(pageable);
        List<CartResponse> list = cartMapper.toListCartResponse(carts.getContent());
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Get cart page with user role
     *
     * @param sortBy
     * @param order
     * @param page
     * @param pageSize
     * @param userPrinciple
     * @return GenericResponse
     */
    @GetMapping(path = UrlConstants.CART_URL)
    @ApiOperation(value = "Get Carts Endpoint", notes = "User endpoint")
    public GenericResponse getCartPageWithUserRole(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                                   @RequestParam(value = "order", defaultValue = "desc") String order,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                   @CurrentUser UserPrinciple userPrinciple) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Cart> carts = cartService.findAllByUserId(pageable, userPrinciple.getId());
        List<CartResponse> list = cartMapper.toListCartResponse(carts.getContent());
        return GenericResponse.builder().data(list).build();
    }

    /**
     * Get cart detail with admin role
     *
     * @param cartId
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Get Cart's Detail", notes = "Admin endpoint")
    @GetMapping(path = UrlConstants.ADMIN_URL + UrlConstants.CART_URL + "/{cartId}")
    public GenericResponse getCartById(@PathVariable UUID cartId) throws Exception {
        Cart cart = cartService.findById(cartId);
        CartResponse cartResponse = cartMapper.toCartResponse(cart);
        return GenericResponse.builder().data(cartResponse).build();
    }

    /**
     * Get cart detail with user role
     *
     * @param cartId
     * @param userPrinciple
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Get Cart's Detail", notes = "User endpoint")
    @GetMapping(path = UrlConstants.CART_URL + "/cartId")
    public GenericResponse getCartByIdWithUserRole(@PathVariable UUID cartId, @CurrentUser UserPrinciple userPrinciple)
            throws Exception {
        Cart cart = cartService.findByIdAndUserId(cartId, userPrinciple.getId());
        CartResponse cartResponse = cartMapper.toCartResponse(cart);
        return GenericResponse.builder().data(cartResponse).build();
    }

    /**
     * Create new cart
     *
     * @param cartRequest
     * @param userPrinciple
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Create New Card Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.CART_URL)
    public GenericResponse create(@RequestBody CartRequest cartRequest, @CurrentUser UserPrinciple userPrinciple)
            throws Exception {
        Cart cart = cartService.create(cartRequest, userPrinciple.getId());
        CartResponse cartResponse = cartMapper.toCartResponse(cart);
        return GenericResponse.builder().data(cartResponse).build();
    }

    /**
     * Delete cart
     *
     * @param cartId
     * @param userPrinciple
     * @return GenericResponse
     * @throws Exception
     */
    @ApiOperation(value = "Delete Card Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.CART_URL + "/{cartId}")
    public GenericResponse delete(@PathVariable UUID cartId, @CurrentUser UserPrinciple userPrinciple)
            throws Exception {
        cartService.delete(cartId, userPrinciple.getId());
        return new GenericResponse();
    }

}
