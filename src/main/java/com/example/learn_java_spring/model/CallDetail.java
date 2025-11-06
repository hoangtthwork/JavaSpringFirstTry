package com.example.learn_java_spring.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "CallDetail")
public class CallDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CommentId")
    private Integer commentId;

    @Column(name = "Guid")
    private String guid;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "CallDuration")
    private Integer callDuration;

    @Column(name = "CallType")
    private Integer callType;

    @Column(name = "CallUserCode")
    private String callUserCode;

    @Column(name = "CallUserName")
    private String callUserName;

    @Column(name = "CallShopCode")
    private String callShopCode;

    @Column(name = "CallShopName")
    private String callShopName;

    @Column(name = "Role")
    private String role;

    @Column(name = "CallCustomer")
    private String callCustomer;

    @Column(name = "CallStatus")
    private Integer callStatus;

    @Column(name = "CallNoteResult")
    private String callNoteResult;

    @Column(name = "LinkRecord")
    private String linkRecord;

    @Column(name = "Phone")
    private String phone;
}