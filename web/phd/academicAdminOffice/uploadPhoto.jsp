<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@page import="net.sourceforge.fenixedu.domain.phd.candidacy.PhdCandidacyDocumentUploadBean"%>
<%@page import="net.sourceforge.fenixedu.domain.phd.PhdIndividualProgramDocumentType"%><html:xhtml/>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>


<logic:present role="ACADEMIC_ADMINISTRATIVE_OFFICE">
<bean:define id="processId" name="process" property="externalId" />

<%-- ### Title #### --%>
<em><bean:message  key="label.phd.academicAdminOffice.breadcrumb" bundle="PHD_RESOURCES"/></em>
<h2><bean:message key="label.phd.studyPlanAndQualificationExams" bundle="PHD_RESOURCES" /></h2>
<%-- ### End of Title ### --%>


<%--  ###  Return Links / Steps Information(for multistep forms)  ### --%>
<html:link action="<%= "/phdIndividualProgramProcess.do?method=viewProcess&processId=" + processId.toString() %>">
	<bean:message bundle="PHD_RESOURCES" key="label.back"/>
</html:link>
<br/><br/>
<%--  ### Return Links / Steps Information (for multistep forms)  ### --%>

<%--  ### Error Messages  ### --%>
<jsp:include page="/phd/errorsAndMessages.jsp" />
<%--  ### End of Error Messages  ### --%>


<%--  ### Context Information (e.g. Person Information, Registration Information)  ### --%>
<strong><bean:message  key="label.phd.process" bundle="PHD_RESOURCES"/></strong>
<fr:view schema="PhdIndividualProgramProcess.view" name="process">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2 thlight mtop15" />
	</fr:layout>
</fr:view>
<%--  ### End Of Context Information  ### --%>

<br/><br/>

<%--  ### Operation Area (e.g. Create Candidacy)  ### --%>
<%--  Photo Upload --%>
<fr:edit id="uploadPhotoBean" name="uploadPhotoBean" schema="PhotographUploadBean.upload" 
	action="<%= "/phdIndividualProgramProcess.do?method=uploadPhoto&processId=" + processId.toString() %>">		
	<fr:layout name="tabular-editable">
		<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
		<fr:property name="columnClasses" value=",,tdclear tderror1" />
		<fr:property name="requiredMarkShown" value="true" />
	</fr:layout>
	<fr:destination name="invalid" path="<%= "/phdIndividualProgramProcess.do?method=uploadPhotoInvalid&processId=" + processId.toString() %>" />
	<fr:destination name="cancel" path="<%= "/phdIndividualProgramProcess.do?method=viewProcess&processId=" + processId.toString() %>"/>
</fr:edit>

<%--  ### End of Operation Area  ### --%>



<%--  ### Buttons (e.g. Submit)  ### --%>

<%--  ### End of Buttons (e.g. Submit)  ### --%>


</logic:present>
