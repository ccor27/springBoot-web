package com.springboot.app.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String description;
    private String comment;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private List<ItemReceipt> items;

    @PrePersist
    public void prePersist(){
    createAt =new Date();
    }

    public Receipt(){
    items = new ArrayList<ItemReceipt>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @XmlTransient
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ItemReceipt> getItems() {
        return items;
    }

    public void setItems(List<ItemReceipt> items) {
        this.items = items;
    }

    public void addItem(ItemReceipt item){
        this.items.add(item);
    }

    public Double getTotal(){
         return items.stream().mapToDouble(ItemReceipt::calculateAmount).sum();
    }
}
