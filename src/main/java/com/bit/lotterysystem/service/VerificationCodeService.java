package com.bit.lotterysystem.service;

public interface VerificationCodeService {
    void setVerificationCodeService(String email);

    String getVerificationCodeService(String email);
}
