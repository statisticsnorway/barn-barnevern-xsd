package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.AvgiverType

class ReporterUrbanDistrictNumberAndName : AbstractRule(
    WarningLevel.ERROR,
    "Avgiver Kontroll 1: Bydelsnummer og bydelsnavn",
    AvgiverType::class.java.simpleName
) {
    companion object{

        const val OSLO_COMPANY_ID = "958935420"
        const val BERGEN_COMPANY_ID = "964338531"
        const val TRONDHEIM_COMPANY_ID = "942110464"

        private val businessIdList = setOf(
            OSLO_COMPANY_ID,
            BERGEN_COMPANY_ID,
            TRONDHEIM_COMPANY_ID,
        )
    }

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        if (context.rootObject.avgiver.organisasjonsnummer !in businessIdList) {
            return null
        }

        return if (context.rootObject.avgiver.bydelsnummer.isNullOrEmpty()
            || context.rootObject.avgiver.bydelsnavn.isNullOrEmpty())
            createSingleReportEntryList(
                "Bydelsnummer og/eller Bydelsnavn skal være utfylt",
                context.rootObject.id!!)
        else
            null
    }
}