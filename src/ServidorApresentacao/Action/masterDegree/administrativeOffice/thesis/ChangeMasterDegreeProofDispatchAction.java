package ServidorApresentacao.Action.masterDegree.administrativeOffice.thesis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import DataBeans.InfoMasterDegreeProofVersion;
import DataBeans.InfoMasterDegreeThesisDataVersion;
import DataBeans.InfoStudentCurricularPlan;
import ServidorAplicacao.IUserView;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorAplicacao.Servico.exceptions.NonExistingServiceException;
import ServidorAplicacao.Servico.exceptions.ScholarshipNotFinishedServiceException;
import ServidorApresentacao.Action.exceptions.FenixActionException;
import ServidorApresentacao.Action.exceptions.NonExistingActionException;
import ServidorApresentacao.Action.exceptions.ScholarshipNotFinishedActionException;
import ServidorApresentacao.Action.sop.utils.ServiceUtils;
import ServidorApresentacao.Action.sop.utils.SessionConstants;
import ServidorApresentacao.Action.sop.utils.SessionUtils;
import Util.MasterDegreeClassification;
import Util.TipoCurso;

/**
 * 
 * @author :
 *   - Shezad Anavarali (sana@mega.ist.utl.pt)
 *   - Nadir Tarmahomed (naat@mega.ist.utl.pt)
 *
 */

public class ChangeMasterDegreeProofDispatchAction extends DispatchAction {

	public ActionForward getStudentAndMasterDegreeProofVersion(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		IUserView userView = SessionUtils.getUserView(request);

		Integer degreeType = Integer.valueOf(request.getParameter("degreeType"));
		Integer studentNumber = Integer.valueOf(request.getParameter("studentNumber"));

		MasterDegreeThesisOperations operations = new MasterDegreeThesisOperations();
		ActionErrors actionErrors = new ActionErrors();
		boolean isSuccess = operations.getStudentByNumberAndDegreeType(form, request, actionErrors);

		if (isSuccess == false) {
			throw new NonExistingActionException("error.exception.masterDegree.nonExistentStudent", mapping.findForward("error"));

		}

		InfoStudentCurricularPlan infoStudentCurricularPlan = null;
		InfoMasterDegreeProofVersion infoMasterDegreeProofVersion = null;
		InfoMasterDegreeThesisDataVersion infoMasterDegreeThesisDataVersion = null;

		/* * * get student curricular plan * * */
		Object argsStudentCurricularPlan[] = { studentNumber, new TipoCurso(degreeType)};
		try {
			infoStudentCurricularPlan =
				(InfoStudentCurricularPlan) ServiceUtils.executeService(
					userView,
					"student.ReadActiveStudentCurricularPlanByNumberAndDegreeType",
					argsStudentCurricularPlan);
		} catch (FenixServiceException e) {
			throw new FenixActionException(e);
		}

		if (infoStudentCurricularPlan == null) {
			throw new NonExistingActionException("error.exception.masterDegree.nonExistentActiveStudentCurricularPlan", mapping.findForward("error"));
		}

		/* * * get master degree thesis data * * */
		Object argsMasterDegreeThesisDataVersion[] = { infoStudentCurricularPlan };
		try {
			infoMasterDegreeThesisDataVersion =
				(InfoMasterDegreeThesisDataVersion) ServiceUtils.executeService(
					userView,
					"ReadActiveMasterDegreeThesisDataVersionByStudentCurricularPlan",
					argsMasterDegreeThesisDataVersion);
		} catch (NonExistingServiceException e) {
			throw new NonExistingActionException("error.exception.masterDegree.nonExistingMasterDegreeThesis", mapping.findForward("error"));

		} catch (FenixServiceException e) {
			throw new FenixActionException(e);
		}

		request.setAttribute(SessionConstants.DISSERTATION_TITLE, infoMasterDegreeThesisDataVersion.getDissertationTitle());

		List finalResult = MasterDegreeClassification.toArrayList();
		request.setAttribute(SessionConstants.CLASSIFICATION, finalResult);

		/* * * get master degree proof * * */
		Object argsMasterDegreeProofVersion[] = { infoStudentCurricularPlan };
		try {
			infoMasterDegreeProofVersion =
				(InfoMasterDegreeProofVersion) ServiceUtils.executeService(
					userView,
					"ReadActiveMasterDegreeProofVersionByStudentCurricularPlan",
					argsMasterDegreeProofVersion);
		} catch (NonExistingServiceException e) {
			//throw new NonExistingActionException("error.exception.masterDegree.nonExistingMasterDegreeThesis", mapping.findForward("error"));
			infoMasterDegreeProofVersion = new InfoMasterDegreeProofVersion();

			DynaActionForm changeMasterDegreeThesisForm = (DynaActionForm) form;

			changeMasterDegreeThesisForm.set("studentNumber", studentNumber);
			changeMasterDegreeThesisForm.set("degreeType", degreeType);
			changeMasterDegreeThesisForm.set("dissertationTitle", infoMasterDegreeThesisDataVersion.getDissertationTitle());
			changeMasterDegreeThesisForm.set("finalResult", new Integer(MasterDegreeClassification.UNDEFINED_TYPE));
			changeMasterDegreeThesisForm.set("attachedCopiesNumber", new Integer(0));
			changeMasterDegreeThesisForm.set("proofDateDay", new Integer(0));
			changeMasterDegreeThesisForm.set("proofDateMonth", new Integer(0));
			changeMasterDegreeThesisForm.set("proofDateYear", new Integer(0));
			changeMasterDegreeThesisForm.set("thesisDeliveryDateDay", new Integer(0));
			changeMasterDegreeThesisForm.set("thesisDeliveryDateMonth", new Integer(0));
			changeMasterDegreeThesisForm.set("thesisDeliveryDateYear", new Integer(0));

			return mapping.findForward("start");

		} catch (ScholarshipNotFinishedServiceException e) {
			throw new ScholarshipNotFinishedActionException("error.exception.masterDegree.scholarshipNotFinished", mapping.findForward("error"));
		} catch (FenixServiceException e) {
			throw new FenixActionException(e);
		}

		if (infoMasterDegreeProofVersion.getInfoJuries().isEmpty() == false)
			request.setAttribute(SessionConstants.JURIES_LIST, infoMasterDegreeProofVersion.getInfoJuries());

		DynaActionForm changeMasterDegreeThesisForm = (DynaActionForm) form;

		changeMasterDegreeThesisForm.set("studentNumber", studentNumber);
		changeMasterDegreeThesisForm.set("degreeType", degreeType);
		changeMasterDegreeThesisForm.set("dissertationTitle", infoMasterDegreeThesisDataVersion.getDissertationTitle());
		changeMasterDegreeThesisForm.set("finalResult", new Integer(infoMasterDegreeProofVersion.getFinalResult().getValue()));
		changeMasterDegreeThesisForm.set("attachedCopiesNumber", infoMasterDegreeProofVersion.getAttachedCopiesNumber());
		changeMasterDegreeThesisForm.set("proofDateDay", new Integer(infoMasterDegreeProofVersion.getProofDate().getDate()));
		changeMasterDegreeThesisForm.set("proofDateMonth", new Integer(infoMasterDegreeProofVersion.getProofDate().getMonth()));
		changeMasterDegreeThesisForm.set("proofDateYear", new Integer(infoMasterDegreeProofVersion.getProofDate().getYear()));
		changeMasterDegreeThesisForm.set("thesisDeliveryDateDay", new Integer(infoMasterDegreeProofVersion.getThesisDeliveryDate().getDate()));
		changeMasterDegreeThesisForm.set("thesisDeliveryDateMonth", new Integer(infoMasterDegreeProofVersion.getThesisDeliveryDate().getMonth()));
		changeMasterDegreeThesisForm.set("thesisDeliveryDateYear", new Integer(infoMasterDegreeProofVersion.getThesisDeliveryDate().getYear()));

		return mapping.findForward("start");

	}

}