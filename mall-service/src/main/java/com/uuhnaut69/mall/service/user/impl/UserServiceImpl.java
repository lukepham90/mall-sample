package com.uuhnaut69.mall.service.user.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.payload.request.UserBaseContent;
import com.uuhnaut69.mall.repository.user.UserRepository;
import com.uuhnaut69.mall.service.product.ProductService;
import com.uuhnaut69.mall.service.tag.TagService;
import com.uuhnaut69.mall.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TagService tagService;

    private final ProductService productService;

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new BadRequestException(MessageConstant.USER_NOT_FOUND));
    }

    @Override
    public void save(User user) throws Exception {
        userRepository.save(user);
    }

    @Override
    public void initBaseContent(UUID id, UserBaseContent userBaseContent) throws Exception {
        User user = findById(id);
        Set<Tag> tags = tagService.findListTagInListIds(userBaseContent.getUuidTags());
        user.setTags(tags);
    }

    @Override
    public void markAsReadProduct(UUID userId, UUID productId) throws Exception {
        User user = findById(userId);
        Product product = productService.findById(productId);

        if (!user.getProducts().isEmpty()) {
            boolean checkExist = user.getProducts().stream().anyMatch(e -> e.getId().equals(productId));
            if (checkExist) {
                user.getProducts().removeIf(e -> e.getId().equals(productId));
            }
        }
        user.getProducts().add(product);
        userRepository.save(user);
    }

}
