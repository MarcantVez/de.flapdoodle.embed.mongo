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

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.process.extract.UUIDTempNaming;
import de.flapdoodle.embed.process.io.directories.PropertyOrPlatformTempDir;
import de.flapdoodle.embed.process.store.ArtifactStore;
import de.flapdoodle.embed.process.store.CachingArtifactStore;
import de.flapdoodle.embed.process.store.UrlConnectionDownloader;

@Deprecated
/**
 * @see ExtractedArtifactStoreBuilder
 * @author mosmann
 *
 */
public class ArtifactStoreBuilder {

	public static CachingArtifactStore defaults(Command command) {
		return ArtifactStore.builder()
			.tempDirFactory(new PropertyOrPlatformTempDir())
			.executableNaming(new UUIDTempNaming())
			.downloadConfig(Defaults.downloadConfigFor(command).build())
			.downloader(new UrlConnectionDownloader())
			.build()
			.withCache();
	}
}
