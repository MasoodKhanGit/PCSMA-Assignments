package com.masood.assignment4.pdfupload.controller;

import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("pdfUserDetailsService")
public class PdfUserDetailsService implements UserDetailsService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		logger.info("loadUserByUsername username=" + username);

		if (username.equals("masood")) {
			return new UserDetails() {

				private static final long serialVersionUID = 2059202961588104658L;

				@Override
				public boolean isEnabled() {
					return true;
				}

				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}

				@Override
				public boolean isAccountNonLocked() {
					return true;
				}

				@Override
				public boolean isAccountNonExpired() {
					return true;
				}

				@Override
				public String getUsername() {
					return username;
				}

				@Override
				public String getPassword() {
					return "pathan";
				}

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
					auths.add(new SimpleGrantedAuthority("User"));
					return auths;
				}
			};

		} else if (username.equals("rahul")) {
			return new UserDetails() {

				private static final long serialVersionUID = 2059202961588104658L;

				@Override
				public boolean isEnabled() {
					return true;
				}

				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}

				@Override
				public boolean isAccountNonLocked() {
					return true;
				}

				@Override
				public boolean isAccountNonExpired() {
					return true;
				}

				@Override
				public String getUsername() {
					return username;
				}

				@Override
				public String getPassword() {
					return "kohli";
				}

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
					auths.add(new SimpleGrantedAuthority("Admin"));
					return auths;
				}
			};

		} else {
			throw new UsernameNotFoundException(username + " not found");
		}

	}
}