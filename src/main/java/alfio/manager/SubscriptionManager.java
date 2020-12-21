/**
 * This file is part of alf.io.
 *
 * alf.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alf.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alf.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package alfio.manager;

import alfio.model.SubscriptionDescriptor;
import alfio.repository.SubscriptionRepository;
import alfio.repository.user.OrganizationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Component
@Transactional
public class SubscriptionManager {

    private final SubscriptionRepository subscriptionRepository;
    private final OrganizationRepository organizationRepository;

    public SubscriptionManager(SubscriptionRepository subscriptionRepository,
                               OrganizationRepository organizationRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<SubscriptionDescriptor> findAll(int organizationId, Principal principal) {
        var userName = principal.getName();
        var access = organizationRepository.hasAccess(userName, organizationId);
        return access ? subscriptionRepository.findAllByOrganizationIds(organizationId) : List.of();
    }
}
