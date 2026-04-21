package com.bit.lotterysystem.service;

public interface VerificationCodeService {
    void sendVerificationCodeService(String email);

    String getVerificationCodeService(String email);
}
