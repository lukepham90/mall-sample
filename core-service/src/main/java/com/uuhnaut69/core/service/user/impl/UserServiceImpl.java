package com.uuhnaut69.core.service.user.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.BadRequestException;
import com.uuhnaut69.core.domain.model.Product;
import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.core.payload.request.UserBaseContent;
import com.uuhnaut69.core.repository.user.UserRepository;
import com.uuhnaut69.core.service.product.ProductService;
import com.uuhnaut69.core.service.tag.TagService;
import com.uuhnaut69.core.service.user.UserService;
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
  public User findById(UUID id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new BadRequestException(MessageConstant.USER_NOT_FOUND));
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public void initBaseContent(UUID id, UserBaseContent userBaseContent) {
    User user = findById(id);
    Set<Tag> tags = tagService.findListTagInListIds(userBaseContent.getUuidTags());
    user.setTags(tags);
  }

  @Override
  public void markAsReadProduct(UUID userId, UUID productId) {
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
