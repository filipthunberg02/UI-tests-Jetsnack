package com.example.jetsnack

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AppTest::class,
    HealthySnackTest::class
)

class AllTests