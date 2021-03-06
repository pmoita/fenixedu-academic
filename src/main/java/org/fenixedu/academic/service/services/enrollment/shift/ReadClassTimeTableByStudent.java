/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.service.services.enrollment.shift;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fenixedu.academic.domain.ExecutionCourse;
import org.fenixedu.academic.domain.SchoolClass;
import org.fenixedu.academic.domain.Shift;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.dto.InfoLessonInstanceAggregation;
import org.fenixedu.academic.dto.InfoShowOccupation;
import org.fenixedu.academic.service.filter.enrollment.ClassEnrollmentAuthorizationFilter;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;
import org.fenixedu.academic.service.services.exceptions.NotAuthorizedException;

import pt.ist.fenixframework.Atomic;

public class ReadClassTimeTableByStudent {

    public List<InfoShowOccupation> run(final Registration registration, final SchoolClass schoolClass,
            final ExecutionCourse executionCourse) throws FenixServiceException {

        if (registration == null) {
            throw new FenixServiceException("error.readClassTimeTableByStudent.noStudent");
        }

        if (schoolClass == null) {
            throw new FenixServiceException("error.readClassTimeTableByStudent.noSchoolClass");
        }

        final Set<ExecutionCourse> attendingExecutionCourses =
                registration.getAttendingExecutionCoursesForCurrentExecutionPeriod();

        final List<InfoShowOccupation> result = new ArrayList<InfoShowOccupation>();
        for (final Shift shift : schoolClass.getAssociatedShiftsSet()) {
            if ((executionCourse == null || executionCourse == shift.getDisciplinaExecucao())
                    && attendingExecutionCourses.contains(shift.getDisciplinaExecucao())) {
                result.addAll(InfoLessonInstanceAggregation.getAggregations(shift));
            }
        }
        return result;
    }

    // Service Invokers migrated from Berserk

    private static final ReadClassTimeTableByStudent serviceInstance = new ReadClassTimeTableByStudent();

    @Atomic
    public static List<InfoShowOccupation> runReadClassTimeTableByStudent(Registration registration, SchoolClass schoolClass,
            ExecutionCourse executionCourse) throws FenixServiceException, NotAuthorizedException {
        ClassEnrollmentAuthorizationFilter.instance.execute(registration);
        return serviceInstance.run(registration, schoolClass, executionCourse);
    }

}