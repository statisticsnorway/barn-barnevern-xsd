package no.ssb.barn.validation

import no.ssb.barn.report.ValidationReport

interface ValidatorContract {
    fun validate(context: ValidationContext): ValidationReport
}