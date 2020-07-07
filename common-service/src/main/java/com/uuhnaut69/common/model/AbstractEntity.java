package com.uuhnaut69.common.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author uuhnaut
 * @project mall
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Timestamp createdDate;

  @LastModifiedDate private Timestamp updatedDate;
}
