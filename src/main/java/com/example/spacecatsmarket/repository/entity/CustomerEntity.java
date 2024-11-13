package com.example.spacecatsmarket.repository.entity;

import com.example.spacecatsmarket.common.CommunicationChannel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq")
    Long id;

    String name;
    String email;
    String address;
    String phoneNumber;

    @NaturalId
    @Column(nullable = false, unique = true)
    UUID customerReference;

    @ElementCollection(targetClass = CommunicationChannel.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_communication_channels", joinColumns = @JoinColumn(name = "customer_id"))
    @Enumerated(EnumType.ORDINAL)
    List<CommunicationChannel> communicationChannel;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    List<OrderEntity> orders;
}
