# Octopus Deploy Configuration Guide

## Environment Setup
- Development
- QA
- Production

## Deployment Targets
- Windows Server
- Roles: api-test-runner

## Variables
- API_BASE_URL: Base URL for API testing
- API_KEY: API authentication key
- EMAIL_RECIPIENTS: Email notification recipients

## Process
1. Run API Tests
   - Execute Maven tests
   - Generate reports
   - Send notifications

## Requirements
- Windows Server with:
  - Java 11
  - Maven
  - PowerShell
