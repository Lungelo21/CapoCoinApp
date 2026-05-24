package com.example.capocoinapp.Supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://kwiotoqaefsudpvvfdnf.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt3aW90b3FhZWZzdWRwdnZmZG5mIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg2MjY4OTcsImV4cCI6MjA5NDIwMjg5N30.py3otMvKwBjIqwOoAvl42nhrPqZMgRfK1pn1wd5PtpY"
    ){
        //Used for Authentication - Login/Register
        //install(Auth)

        //Used to help insert details into the tables
        install(Postgrest)

        //Used for image uploads - transaction images
        install(Storage)
    }
}