package hu.elte.inetsense.server.web.service.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import hu.elte.inetsense.server.data.entities.User;

/**
 * @author Zsolt Istvanfi
 */
public class InetsenseUserDetails implements UserDetails {

    private static final long        serialVersionUID = 1L;

    private final User               user;
    private final Collection<String> roles;

    public InetsenseUserDetails(final User user, final Collection<String> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolesAsString = StringUtils.collectionToCommaDelimitedString(roles);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(rolesAsString);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
