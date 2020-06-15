package com.uuhnaut69.search.service.index;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.TagEs;
import com.uuhnaut69.search.document.UserEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserEsService {

  void handleCdcEvent(Map<String, Object> userData, Operation operation);

  UserEs findById(String id);

  void save(UserEs userEs);

  void findByTagAndUpdate(String tagNameBefore, TagEs tagEs);
}
