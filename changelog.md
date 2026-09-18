# Changelog

All notable changes to VEHMS are documented here.
Format: `## [version] - date` then a bullet per ticket completed.

## [0.1.0] - Module 1 setup
- VEHMS-M01-T001: Initial Spring Boot project created (Java 17, Maven)
- VEHMS-M01-T002: pgAdmin + PostgreSQL configured locally
- VEHMS-M01-T003: Database connection configured, changelog.md initialized
- VEHMS-M01-T004: Connection to pgAdmin verified
- VEHMS-M01-T005: Resident, Vehicle, Visitor entities implemented with Lombok + enums
- VEHMS-M01-T006: Tables auto-created via Hibernate ddl-auto=update, one-to-many mappings verified
- VEHMS-M01-T007: POST /api/residents - create resident with vehicles
- VEHMS-M01-T008: Swagger/OpenAPI documentation added
- VEHMS-M01-T010: GET /api/residents - list all residents with vehicles
- VEHMS-M01-T012: GET /api/residents/search - search by name
- VEHMS-M01-T015: POST /api/vehicles - register vehicle to existing resident
- VEHMS-M01-T017: GET /api/vehicles/search - resident lookup by registration number

## [0.2.0] - Module 2: Visitors
- VEHMS-M02-T020: POST /api/visitors - log new visitor mapped to a resident
- VEHMS-M02-T022: GET /api/visitors/search - resident+visitor details by vehicle registration number
- VEHMS-M02-T024: PATCH /api/visitors/exit - mark visitor exit, compute visit duration
- VEHMS-M02-T026: GET /api/visitors/active - active visitors, optional GUEST/DELIVERY filter
- VEHMS-M02-T028: Visitor.visitDuration field added (HH:MM format)
- VEHMS-M02-T031: Nightly scheduler (11 PM) backs up the day's visitors to
  visitors log/Visitors_history_log_DDMMYYYY.xlsx, auto-creating the folder
