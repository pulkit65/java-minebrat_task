package com.java_minebrat_task.entity;

    @Entity
    @Table(name = "audit_logs")
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public class AuditLog {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = false)
        private String entityType; // e.g. USER, ADDRESS, IMPORT_JOB

        private UUID entityId;

        @Column(nullable = false)
        private String action; // e.g. CREATE, UPDATE, DELETE, IMPORT

        @Column(nullable = false)
        private String performedBy;

        @Lob
        private String details; // can hold JSON or text

        @CreationTimestamp
        private Instant timestamp;
    }
