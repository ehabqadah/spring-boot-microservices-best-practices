package com.qadah.demo.data.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;


/**
 * <p> Root entity for object persistence via JPA.</p>
 * @author Ehab Qadah
 */
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(generator = "custom-generator", strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "custom-generator", strategy = "com.qadah.demo.data.model.id.generator.BaseIdentifierGenerator")
	protected String id;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	protected Instant createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	protected Instant modifiedAt;

	@Column
	@Version
	protected int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Instant modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *Indicates whether the object has already been persisted or not
	 *
	 * @return true if the object has not yet been persisted
	 */
	public boolean isNew() {
		return getId() == null;
	}
	@Override
	public String toString() {
		return id;
	}
}
