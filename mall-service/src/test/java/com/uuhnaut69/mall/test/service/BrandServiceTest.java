package com.uuhnaut69.mall.test.service;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Brand;
import com.uuhnaut69.mall.repository.brand.BrandRepository;
import com.uuhnaut69.mall.service.brand.impl.BrandServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * @author uuhnaut
 * @project mall
 */
@RunWith(SpringRunner.class)
public class BrandServiceTest {

    @MockBean
    private BrandServiceImpl brandService;

    @MockBean
    private BrandRepository brandRepository;

    @Test
    public void deleteBrandSuccessful() {
        UUID id = UUID.randomUUID();
        Brand brand = new Brand();
        brand.setBrandName("Nike");
        brand.setLogo("Nike");
        when(brandService.findById(id)).thenReturn(brand);
        brandService.delete(id);
        verify(brandService, times(1)).delete(any());
    }

    @Test
    public void deleteBrandFailure() {
        when(brandService.findById(UUID.randomUUID())).thenThrow(new NotFoundException(MessageConstant.BRAND_NOT_FOUND));
        verify(brandRepository, never()).delete(any());
    }
}
