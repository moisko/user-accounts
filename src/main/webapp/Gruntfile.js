module.exports = function(grunt) {
	grunt.initConfig({
		jshint : [ 'js/account/*.js' ],
		concat : {
			js : {
				files : {
					'build/js/account/bundle.js': 'js/account/*.js'
				}
			}
		},
		uglify : {
			bundle : {
				files : {
					'build/js/account/bundle.min.js': 'build/js/account/bundle.js'
				}
			}
		},
		clean : {
			js : 'build/js/account/bundle.js'
		}
	});
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-clean');

	grunt.registerTask('dist', [ 'jshint', 'concat:js', 'uglify:bundle', 'clean:js' ]);
};