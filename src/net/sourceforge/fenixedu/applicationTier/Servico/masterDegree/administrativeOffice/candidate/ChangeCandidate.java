/**
 * 
 * Autores : - Nuno Nunes (nmsn@rnl.ist.utl.pt) - Joana Mota
 * (jccm@rnl.ist.utl.pt)
 *  
 */

package net.sourceforge.fenixedu.applicationTier.Servico.masterDegree.administrativeOffice.candidate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import net.sourceforge.fenixedu.applicationTier.Servico.ExcepcaoInexistente;
import net.sourceforge.fenixedu.applicationTier.Servico.commons.SendMail;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.InfoMasterDegreeCandidate;
import net.sourceforge.fenixedu.dataTransferObject.InfoMasterDegreeCandidateWithInfoPerson;
import net.sourceforge.fenixedu.domain.CandidateSituation;
import net.sourceforge.fenixedu.domain.ICandidateSituation;
import net.sourceforge.fenixedu.domain.ICountry;
import net.sourceforge.fenixedu.domain.IMasterDegreeCandidate;
import net.sourceforge.fenixedu.domain.IPerson;
import net.sourceforge.fenixedu.domain.MasterDegreeCandidate;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import net.sourceforge.fenixedu.util.State;
import pt.utl.ist.berserk.logic.serviceManager.IService;

/**
 * 
 * @author - Shezad Anavarali (shezad@ist.utl.pt)
 * 
 */
public class ChangeCandidate implements IService {

    public InfoMasterDegreeCandidate run(Integer oldCandidateID, InfoMasterDegreeCandidate newCandidate)
            throws ExcepcaoInexistente, FenixServiceException, ExcepcaoPersistencia {

        ISuportePersistente sp = PersistenceSupportFactory.getDefaultPersistenceSupport();

        IMasterDegreeCandidate masterDegreeCandidate = (IMasterDegreeCandidate) sp
                .getIPersistentMasterDegreeCandidate().readByOID(MasterDegreeCandidate.class,
                        oldCandidateID, true);

        if (masterDegreeCandidate == null) {
            throw new ExcepcaoInexistente("Unknown Candidate !!");
        }

        // Change personal Information
        IPerson person = masterDegreeCandidate.getPerson();
        person.setIdDocumentType(newCandidate.getInfoPerson().getTipoDocumentoIdentificacao());
        person.setNumeroDocumentoIdentificacao(newCandidate.getInfoPerson()
                .getNumeroDocumentoIdentificacao());
        if ((newCandidate.getInfoPerson().getInfoPais() != null)) {
            ICountry nationality = sp.getIPersistentCountry().readCountryByNationality(
                    newCandidate.getInfoPerson().getInfoPais().getNationality());
            person.setPais(nationality);
        }
        person.setNascimento(newCandidate.getInfoPerson().getNascimento());
        person.setDataEmissaoDocumentoIdentificacao(newCandidate.getInfoPerson()
                .getDataEmissaoDocumentoIdentificacao());
        person.setDataValidadeDocumentoIdentificacao(newCandidate.getInfoPerson()
                .getDataValidadeDocumentoIdentificacao());
        person.setLocalEmissaoDocumentoIdentificacao(newCandidate.getInfoPerson()
                .getLocalEmissaoDocumentoIdentificacao());
        person.setNome(newCandidate.getInfoPerson().getNome());
        person.setGender(newCandidate.getInfoPerson().getSexo());
        person.setMaritalStatus(newCandidate.getInfoPerson().getMaritalStatus());
        person.setNomePai(newCandidate.getInfoPerson().getNomePai());
        person.setNomeMae(newCandidate.getInfoPerson().getNomeMae());
        person.setFreguesiaNaturalidade(newCandidate.getInfoPerson().getFreguesiaNaturalidade());
        person.setConcelhoNaturalidade(newCandidate.getInfoPerson().getConcelhoNaturalidade());
        person.setDistritoNaturalidade(newCandidate.getInfoPerson().getDistritoNaturalidade());
        person.setLocalidadeCodigoPostal(newCandidate.getInfoPerson().getLocalidadeCodigoPostal());
        person.setMorada(newCandidate.getInfoPerson().getMorada());
        person.setLocalidade(newCandidate.getInfoPerson().getLocalidade());
        person.setCodigoPostal(newCandidate.getInfoPerson().getCodigoPostal());
        person.setFreguesiaMorada(newCandidate.getInfoPerson().getFreguesiaMorada());
        person.setConcelhoMorada(newCandidate.getInfoPerson().getConcelhoMorada());
        person.setDistritoMorada(newCandidate.getInfoPerson().getDistritoMorada());
        person.setTelefone(newCandidate.getInfoPerson().getTelefone());
        person.setTelemovel(newCandidate.getInfoPerson().getTelemovel());
        person.setEmail(newCandidate.getInfoPerson().getEmail());
        person.setEnderecoWeb(newCandidate.getInfoPerson().getEnderecoWeb());
        person.setNumContribuinte(newCandidate.getInfoPerson().getNumContribuinte());
        person.setProfissao(newCandidate.getInfoPerson().getProfissao());
        person.setNacionalidade(newCandidate.getInfoPerson().getNacionalidade());
        sp.getIPessoaPersistente().simpleLockWrite(person);

        // Change Candidate Information
        masterDegreeCandidate.setAverage(newCandidate.getAverage());
        masterDegreeCandidate.setMajorDegree(newCandidate.getMajorDegree());
        masterDegreeCandidate.setMajorDegreeSchool(newCandidate.getMajorDegreeSchool());
        masterDegreeCandidate.setMajorDegreeYear(newCandidate.getMajorDegreeYear());
        masterDegreeCandidate.setSpecializationArea(newCandidate.getSpecializationArea());

        // Change Situation
        ICandidateSituation oldCandidateSituation = masterDegreeCandidate.getActiveCandidateSituation();
        if (!oldCandidateSituation.getSituation().equals(
                newCandidate.getInfoCandidateSituation().getSituation())) {

            oldCandidateSituation.setValidation(new State(State.INACTIVE));
            sp.getIPersistentCandidateSituation().simpleLockWrite(oldCandidateSituation);

            ICandidateSituation newCandidateSituation = new CandidateSituation();
            sp.getIPersistentCandidateSituation().simpleLockWrite(newCandidateSituation);
            newCandidateSituation.setDate(Calendar.getInstance().getTime());
            newCandidateSituation.setMasterDegreeCandidate(masterDegreeCandidate);
            newCandidateSituation.setRemarks(newCandidate.getInfoCandidateSituation().getRemarks());
            newCandidateSituation.setSituation(newCandidate.getInfoCandidateSituation().getSituation());
            newCandidateSituation.setValidation(new State(State.ACTIVE));

            masterDegreeCandidate.getSituations().add(newCandidateSituation);

            if (person.getEmail() != null) {
                sendEmailToCandidate(masterDegreeCandidate, newCandidateSituation);
            }
        }

        InfoMasterDegreeCandidate infoMasterDegreeCandidate = InfoMasterDegreeCandidateWithInfoPerson
                .newInfoFromDomain(masterDegreeCandidate);

        return infoMasterDegreeCandidate;

    }

    private void sendEmailToCandidate(IMasterDegreeCandidate masterDegreeCandidate,
            ICandidateSituation candidateSituation) {
        ResourceBundle rb = ResourceBundle.getBundle("ServidorApresentacao.ApplicationResources");
        List toList, CCList, BCCList;
        toList = new ArrayList();
        CCList = new ArrayList();
        BCCList = new ArrayList();
        toList.add(masterDegreeCandidate.getPerson().getEmail());
        String fromName, from, subject;
        fromName = rb.getString("masterDegreeCandidate.email.fromName");
        from = rb.getString("masterDegreeCandidate.email.fromEmail");
        subject = rb.getString("masterDegreeCandidate.email.subject")
                + masterDegreeCandidate.getExecutionDegree().getDegreeCurricularPlan().getName();
        StringBuilder text = new StringBuilder();
        text.append(rb.getString("masterDegreeCandidate.email.greeting"));
        text.append(" ");
        text.append(masterDegreeCandidate.getPerson().getNome());
        text.append(rb.getString("masterDegreeCandidate.email.period"));
        text.append(rb.getString("masterDegreeCandidate.email.newLine"));
        text.append(rb.getString("masterDegreeCandidate.email.body"));
        text.append(candidateSituation.getSituation().toString());
        text.append(rb.getString("masterDegreeCandidate.email.period"));
        text.append(rb.getString("masterDegreeCandidate.email.newLine"));
        text.append(rb.getString("masterDegreeCandidate.email.newLine"));
        text.append(rb.getString("masterDegreeCandidate.email.goodbye"));
        try {
            SendMail sendMailService = new SendMail();
            sendMailService.run(toList, CCList, BCCList, fromName, from, subject, text.toString());
        } catch (Exception e) {
        }
    }
}