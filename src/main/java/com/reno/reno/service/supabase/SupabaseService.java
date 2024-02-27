package com.reno.reno.service.supabase;

import com.reno.reno.model.service.SignUpRequest;
import com.reno.reno.model.service.signup.SignUpResponse;

public interface SupabaseService {
    public SignUpResponse postSignUp(SignUpRequest request) throws Exception;
}