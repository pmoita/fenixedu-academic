package test.not.commit;

import org.fenixedu.academic.domain.accounting.EntryType;
import org.fenixedu.academic.domain.accounting.EventType;
import org.fenixedu.academic.domain.accounting.postingRules.serviceRequests.CertificateRequestPR;
import org.fenixedu.academic.domain.administrativeOffice.AdministrativeOffice;
import org.fenixedu.academic.util.Money;
import org.fenixedu.bennu.scheduler.custom.CustomTask;
import org.joda.time.DateTime;

import pt.ist.fenixframework.FenixFramework;

public class RunCustomTask extends CustomTask {

	@Override
	public void runTask() throws Exception {
		AdministrativeOffice administrativeOffice = FenixFramework
				.getDomainObject("283472136503297");
		CertificateRequestPR postingRule = new CertificateRequestPR(
				EntryType.DEGREE_FINALIZATION_CERTIFICATE_REQUEST_FEE,
				EventType.DEGREE_FINALIZATION_CERTIFICATE_REQUEST,
				new DateTime().minusYears(1), null,
				administrativeOffice.getServiceAgreementTemplate(), new Money(
						"20.00"), new Money("0.00"), Money.ZERO, Money.ZERO);
	}
}