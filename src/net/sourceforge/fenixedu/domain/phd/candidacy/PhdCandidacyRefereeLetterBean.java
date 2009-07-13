package net.sourceforge.fenixedu.domain.phd.candidacy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import net.sourceforge.fenixedu.domain.Country;
import net.sourceforge.fenixedu.domain.DomainReference;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.phd.PhdProgramFocusArea;
import pt.utl.ist.fenix.tools.util.FileUtils;

public class PhdCandidacyRefereeLetterBean implements Serializable {

    static private final long serialVersionUID = 6105525451822275989L;

    private DomainReference<PhdCandidacyReferee> candidacyReferee;
    private DomainReference<PhdCandidacyRefereeLetter> letter;

    private String howLongKnownApplicant;
    private String capacity;
    private String comparisonGroup;
    private String rankInClass;

    private ApplicantOverallPromise academicPerformance;
    private ApplicantOverallPromise socialAndCommunicationSkills;
    private ApplicantOverallPromise potencialToExcelPhd;

    private String comments;

    private String refereeName;
    private String refereePosition;
    private String refereeInstitution;
    private String refereeAddress;
    private String refereeCity;
    private String refereeZipCode;
    private DomainReference<Country> refereeCountry;

    private transient InputStream file;
    private byte[] fileContent;
    private String filename;

    public PhdCandidacyRefereeLetterBean() {
    }

    public PhdCandidacyRefereeLetterBean(final PhdCandidacyRefereeLetter letter) {

	setCandidacyReferee(letter.getCandidacyReferee());
	setLetter(letter);

	setHowLongKnownApplicant(letter.getHowLongKnownApplicant());
	setCapacity(letter.getCapacity());
	setComparisonGroup(letter.getComparisonGroup());
	setRankInClass(letter.getRankInClass());

	setAcademicPerformance(letter.getAcademicPerformance());
	setSocialAndCommunicationSkills(letter.getSocialAndCommunicationSkills());
	setPotencialToExcelPhd(letter.getPotencialToExcelPhd());

	setComments(letter.getComments());

	setRefereeName(letter.getRefereeName());
	setRefereePosition(letter.getRefereePosition());
	setRefereeInstitution(letter.getRefereeInstitution());
	setRefereeAddress(letter.getRefereeAddress());
	setRefereeCity(letter.getRefereeCity());
	setRefereeZipCode(letter.getRefereeZipCode());
	setRefereeCountry(letter.getRefereeCountry());
    }

    public PhdCandidacyReferee getCandidacyReferee() {
	return (this.candidacyReferee != null) ? this.candidacyReferee.getObject() : null;
    }

    public void setCandidacyReferee(final PhdCandidacyReferee candidacyReferee) {
	this.candidacyReferee = (candidacyReferee != null) ? new DomainReference<PhdCandidacyReferee>(candidacyReferee) : null;
    }

    public PhdCandidacyRefereeLetter getLetter() {
	return (this.letter != null) ? this.letter.getObject() : null;
    }

    public void setLetter(final PhdCandidacyRefereeLetter letter) {
	this.letter = (letter != null) ? new DomainReference<PhdCandidacyRefereeLetter>(letter) : null;
    }

    public String getComparisonGroup() {
	return comparisonGroup;
    }

    public void setComparisonGroup(String comparisonGroup) {
	this.comparisonGroup = comparisonGroup;
    }

    public String getRefereeName() {
	return refereeName;
    }

    public void setRefereeName(String refereeName) {
	this.refereeName = refereeName;
    }

    public String getRefereePosition() {
	return refereePosition;
    }

    public void setRefereePosition(String refereePosition) {
	this.refereePosition = refereePosition;
    }

    public String getRefereeInstitution() {
	return refereeInstitution;
    }

    public void setRefereeInstitution(String refereeInstitution) {
	this.refereeInstitution = refereeInstitution;
    }

    public String getRefereeAddress() {
	return refereeAddress;
    }

    public void setRefereeAddress(String refereeAddress) {
	this.refereeAddress = refereeAddress;
    }

    public String getRefereeCity() {
	return refereeCity;
    }

    public void setRefereeCity(String refereeCity) {
	this.refereeCity = refereeCity;
    }

    public String getRefereeZipCode() {
	return refereeZipCode;
    }

    public void setRefereeZipCode(String refereeZipCode) {
	this.refereeZipCode = refereeZipCode;
    }

    public Country getRefereeCountry() {
	return (this.refereeCountry != null) ? this.refereeCountry.getObject() : null;
    }

    public void setRefereeCountry(Country refereeCountry) {
	this.refereeCountry = (refereeCountry != null) ? new DomainReference<Country>(refereeCountry) : null;
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }

    public InputStream getFile() {
	return file;
    }

    public void setFile(InputStream file) {
	this.file = file;

	if (file != null) {
	    final ByteArrayOutputStream result = new ByteArrayOutputStream();
	    try {
		FileUtils.copy(this.file, result);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }

	    this.fileContent = result.toByteArray();
	} else {
	    this.fileContent = null;
	}
    }

    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public byte[] getFileContent() {
	return this.fileContent;
    }

    public boolean hasFileContent() {
	return this.fileContent != null;
    }

    public Person getPerson() {
	return getCandidacyReferee().getPhdProgramCandidacyProcess().getPerson();
    }

    public PhdProgramFocusArea getFocusArea() {
	return getCandidacyReferee().getPhdProgramCandidacyProcess().getIndividualProgramProcess().getPhdProgramFocusArea();
    }

    public String getEmail() {
	return getCandidacyReferee().getEmail();
    }

    public boolean hasLetter() {
	return getLetter() != null;
    }

    public void removeFile() {
	setFile(null);
    }

    public String getHowLongKnownApplicant() {
	return howLongKnownApplicant;
    }

    public void setHowLongKnownApplicant(String howLongKnownApplicant) {
	this.howLongKnownApplicant = howLongKnownApplicant;
    }

    public String getCapacity() {
	return capacity;
    }

    public void setCapacity(String capacity) {
	this.capacity = capacity;
    }

    public String getRankInClass() {
	return rankInClass;
    }

    public void setRankInClass(String rankInClass) {
	this.rankInClass = rankInClass;
    }

    public ApplicantOverallPromise getAcademicPerformance() {
	return academicPerformance;
    }

    public void setAcademicPerformance(ApplicantOverallPromise academicPerformance) {
	this.academicPerformance = academicPerformance;
    }

    public ApplicantOverallPromise getSocialAndCommunicationSkills() {
	return socialAndCommunicationSkills;
    }

    public void setSocialAndCommunicationSkills(ApplicantOverallPromise socialAndCommunicationSkills) {
	this.socialAndCommunicationSkills = socialAndCommunicationSkills;
    }

    public ApplicantOverallPromise getPotencialToExcelPhd() {
	return potencialToExcelPhd;
    }

    public void setPotencialToExcelPhd(ApplicantOverallPromise potencialToExcelPhd) {
	this.potencialToExcelPhd = potencialToExcelPhd;
    }

    public void setCandidacyReferee(DomainReference<PhdCandidacyReferee> candidacyReferee) {
	this.candidacyReferee = candidacyReferee;
    }

    public void setFileContent(byte[] fileContent) {
	this.fileContent = fileContent;
    }

}
