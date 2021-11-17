package no.ssb.barn.framework

import no.ssb.barn.report.ValidationReport

interface ValidatorContract {
    fun validate(context: ValidationContext): ValidationReport
}