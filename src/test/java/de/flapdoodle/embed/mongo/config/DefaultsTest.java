/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.config;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.google.common.collect.ImmutableMap;

import de.flapdoodle.embed.process.io.directories.UserHome;

class DefaultsTest {

	@Test
	void noop() {
//		fail("Not yet implemented");
	}

	@Nested
	class DownloadConfigDefaultsTest {

		@ParameterizedTest(name = "{0}({index})")
		@ArgumentsSource(ArtifactStorePathProvider.class)
		public void artifactStorePathChosenProperly(String description, String expectedDirectory,
				Optional<String> artifactDownloadLocationEnvironmentVariable) {
			ImmutableMap<String, String> env = artifactDownloadLocationEnvironmentVariable
					.map(it -> ImmutableMap.of("EMBEDDED_MONGO_ARTIFACTS", it)).orElseGet(() -> ImmutableMap.of());

			Assert.assertEquals(description, expectedDirectory,
					Defaults.DownloadConfigDefaults.defaultArtifactStoreLocation(env).asFile().getAbsolutePath());
		}

	}

	static class ArtifactStorePathProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			String defaultArtifactDownloadLocation = new UserHome(".embedmongo").asFile().getAbsolutePath();
			return Stream.of(
					Arguments.of("Use home directory when environment variable not present",
							defaultArtifactDownloadLocation, Optional.empty()),
					Arguments.of("Environment variable overrides default when supplied", "/some/explicit/directory",
							Optional.of("/some/explicit/directory")),
					Arguments.of("Environment variable overrides default when supplied", "/another/explicit/directory",
							Optional.of("/another/explicit/directory")));
		}
	}
}
